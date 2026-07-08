package com.practice.webflux.chapter3.application

import com.practice.webflux.chapter3.application.dto.AnswerListResponseDto
import com.practice.webflux.chapter3.application.dto.UserChatRequestDto
import com.practice.webflux.chapter3.application.dto.UserChatResponseDto
import com.practice.webflux.chapter3.client.LlmType
import com.practice.webflux.chapter3.client.LlmWebClient
import com.practice.webflux.chapter3.client.dto.LlmChatRequestDto
import com.practice.webflux.chapter3.exception.CustomErrorType
import com.practice.webflux.chapter3.exception.ErrorTypeException
import com.practice.webflux.chapter3.util.extractJsonString
import com.practice.webflux.chapter3.util.log
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import tools.jackson.module.kotlin.jacksonObjectMapper
import java.util.function.Consumer

@Service
class ChainOfThoughtService(
    private val llmWebClientServiceMap: Map<LlmType, LlmWebClient>,
) {
    val objectMapper = jacksonObjectMapper()


    /**
     * Cot(Chain Of Thought) 를 위해 API와 다중 통신
     *
     * 실행 흐름 (스레드 관점)
     * 1. Flux.create을 통한 Flux 생성 -> Spring WebFlux가 Flux를 자동 구독
     * 2. 이벤트 루프 스레드가 Flux 내의 코드들을 동기적으로 실행 -> Mono 혹은 Flux 흐름을 만나도 실행이 되지는 않음.
     * 3. finalAnswerMono.subscribe -> 구독, Mono 혹은 Flux 함수들 실행
     * 4. 필요한 작업 단계 분석 요청
     *      -> llmWebClient.getChatCompletion().map{}
     *      a. 이벤트 루프 스레드 반환 후 OS가 LLM 응답을 기다림
     *      b. 응답이 반환되었다면, Netty의 이벤트 루프 스레드 할당 후 map 작업 수행
     * 5. 단계별 분석(단계가 3개라고 가정)
     *      -> llmWebClient.getChatCompletionWithCatchException().map{}
     *      a. 한 개의 이벤트 루프 스레드가 3단계에 대한 요청을 각각 요청 후, 이벤트 루프 스레드 반환
     *      b. 세 단계에 대한 요청 처리는 OS가 대기
     *      c. 세 단계에 대한 요청 처리가 완료되었을 경우, 이벤트 루프 스레드를 각각 할당 후, map 수행
     * 6. 단계별 분석 처리 완료 후 -> analyzedCotStep.collectList().flatMap{}
     *      - 3개의 이벤트 루프 스레드 중 가장 늦게 처리된 스레드가 해당 코드를 수행하게 됨
     *
     *      -> llmWebClient.getChatCompletionWithCatchException().map{}
     *      a. 이벤트 루프 스레드 반환 후 OS가 LLM 응답을 기다림
     *      b. 응답이 반환되었다면, Netty의 이벤트 루프 스레드 할당 후 map 작업 수행
     * 7. 마지막 방출되는 데이터 -> subscribe의 finalAnswer로 들어오게 됨
     */
    fun getChainOfThoughtResponse(
        userChatRequestDto: UserChatRequestDto
    ): Flux<UserChatResponseDto> {

        return Flux.create { sink ->

            val userRequest = userChatRequestDto.request
            val requestModel = userChatRequestDto.llmModel

            //유저 프롬프트
            val establishingThoughtChainPrompt = """
                    다음은 사용자의 입력입니다: "$userRequest"
                    사용자에게 체계적으로 답변하기 위해 어떤 단계들이 필요할지 정리해주세요.
                    """.trimIndent()

            //시스템 프롬프트
            val establishingThoughtChainSystemPrompt = """
                    아래처럼 List<String> answerList의 형태를 가지는 JSON FORMAT으로 응답해주세요.
                    <JSONSCHEMA>
                    {
                        "answerList": ["", ...]
                    }
                    </JSONSCHEMA>
                    """.trimIndent()


            val llmWebClient = llmWebClientServiceMap.getValue(requestModel.llmType)

            //1. 필요한 작업 단계 분석
            val cotStepListMono: Mono<AnswerListResponseDto> = llmWebClient.getChatCompletion(
                LlmChatRequestDto(
                    establishingThoughtChainPrompt,
                    establishingThoughtChainSystemPrompt,
                    true,
                    requestModel
                )
            ).map {
                //요청에 대한 llm 응답
                val llmResponse = it.llmResponse

                //답변에서 json 형태만 갖고오기
                val extractedJsonString = extractJsonString(llmResponse!!)

                //json 파싱 후 변환
                try {
                    objectMapper.readValue(
                        extractedJsonString,
                        AnswerListResponseDto::class.java
                    )
                } catch (_: Exception) {
                    throw ErrorTypeException(
                        message = "[JsonParseError] json parse error. extractedJsonString: $extractedJsonString",
                        errorType = CustomErrorType.LLM_RESPONSE_JSON_PARSE_ERROR
                    )
                }
                //데이터가 방출될 때마다, sink로 발행
            }.doOnNext { publishedData ->
                sink.next(
                    UserChatResponseDto(
                        "필요한 작업 단계 분석",
                        publishedData.toString()
                    )
                )
            }


            //2. 단계별 분석
            // Mono<AnswerListResponseDto> -> Flux<String>
            val cotStepFlux: Flux<String> =
                cotStepListMono.flatMapMany { Flux.fromIterable(it.answerList) }

            // Flux<Mono<String>> -> Flux<String> : 단계별 순서대로 요청 및 응답
            val analyzedCotStep: Flux<String> = cotStepFlux.flatMapSequential {
                val cotStepRequestPrompt = """
                        다음은 사용자의 입력입니다: $userRequest
                        
                        사용자의 요구를 다음 단계에 따라 분석해주세요: $it
                        """.trimIndent()

                //Mono
                val answer: Mono<String> = llmWebClient.getChatCompletionWithCatchException(
                    LlmChatRequestDto(
                        cotStepRequestPrompt,
                        "",
                        false,
                        requestModel
                    )
                ).map { result ->
                    result.llmResponse
                        ?: "[오류] ${result.error?.errorMessage ?: "알 수 없는 오류"}"
                }
                answer
            }.doOnNext { publishedData ->
                sink.next(
                    UserChatResponseDto(
                        "단계별 분석",
                        publishedData
                    )
                )
            }

            //3. 최종 답변
            val finalAnswerMono: Mono<String> =
                // Flux<String> -> Mono<List<String>
                analyzedCotStep.collectList()
                    //Mono<List<String>> -> Mono<Mono<String>> -> Mono<String>
                    .flatMap { stepPromptList ->

                        val concatStepPrompt = stepPromptList.joinToString("\n")

                        val finalAnswerPrompt = """
                            다음은 사용자의 입력입니다 : $userRequest
                            아래 사항들을 참고, 분석하여 사용자의 입력에 대한 최종 답변을 해주세요:
                        $concatStepPrompt
                        """.trimIndent()

                        llmWebClient.getChatCompletionWithCatchException(
                            LlmChatRequestDto(
                                finalAnswerPrompt,
                                "",
                                false,
                                requestModel
                            )
                        ).map { it.llmResponse
                            ?: "[오류] ${it.error?.errorMessage ?: "알 수 없는 오류"}" }
                    }

            finalAnswerMono.subscribe(Consumer { finalAnswer: String? ->
                sink.next(UserChatResponseDto("최종 응답", finalAnswer))
                sink.complete()
            }, Consumer { error: Throwable? ->
                log.error("[COT] cot response error", error)
                sink.error(error ?: RuntimeException())
            })

        }
    }

}