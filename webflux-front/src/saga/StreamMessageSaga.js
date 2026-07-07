import {all, fork, take, put, call} from 'redux-saga/effects';
import {cancelled, select, takeLatest} from "@redux-saga/core/effects";
import {
    SEND_MESSAGE_FAIL,
    SEND_MESSAGE_REQUEST
} from "../reducer/MessageReducer";
import {eventChannel} from "@redux-saga/core";


const BASE_URL = 'http://localhost:8080';

const END = {
    end: true
};

function streamChatMessages(response, onMessage, onError) {
    // HTTP 응답이 성공 코드(200) 대역인지 먼저 확인
    if (!response.ok) {
        onError(new Error('Network response was not ok.'));
        return;
    }

    const reader = response.body.getReader();
    const decoder = new TextDecoder();
    let buffer = '';

    // 스트림을 재귀적으로 읽어들이는 함수
    function read() {
        reader.read().then(({ done, value }) => {
            if (done) {
                // EOF에 도달하면 onMessage(END)를 통해 끝남을 알림
                console.log("End: " , END);
                onMessage(END);
                return;
            }

            // 청크를 디코딩하여 buffer에 누적
            buffer += decoder.decode(value, { stream: true });

            // '\n\n'을 기준으로 "완전한 메시지"를 판별
            const boundary = buffer.lastIndexOf('\n\n');
            if (boundary !== -1) {
                const completeMessages = buffer.slice(0, boundary);
                buffer = buffer.slice(boundary + 2); // '\n\n'(길이2) 뒤는 buffer에 남김

                // 완전한 메시지 덩어리를 나눠가며 처리
                completeMessages.split('\n\n').forEach((raw) => {
                    console.log("raw: " , raw);
                    // SSE 형식의 data: 접두어 제거
                    const parsedValue = raw.replace(/data:/g, '');
                    if (parsedValue) {
                        try {
                            const result = JSON.parse(parsedValue);
                            onMessage(result);
                        } catch (error) {
                            console.error(error);
                            onError(error);
                        }
                    }
                });
            }

            // 다음 청크를 계속 읽어들임
            read();
        });
    }

    // 스트림 읽기 시작
    read();

    // eventChannel에서 원하는 시점에 스트림을 취소할 수 있도록
    // reader.cancel() 함수를 반환
    return () => {
        reader.cancel();
    };
}

function* sendStreamMessage(action) {
    try {
        console.log(action.data.urlSelection);
        const handler = action.data.urlSelection.handler;
        yield * handler.onRequest(action);

        // 실제 요청
        const response = yield call(fetch, BASE_URL + action.data.urlSelection.url, {
            method: action.data.urlSelection.method,
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'text/event-stream',
            },
            body: JSON.stringify({
                request: action.data.userInput,
                llmModel: action.data.selectedModel.codeName
            }),
        });

        console.log("response status: ", response);

        if(!response.ok) {
            let errorData = {
                status: response.status,
                statusText: response.statusText,
                url: response.url,
            };
            const httpError = new Error(`HTTP ${errorData.status} - ${errorData.statusText || 'Error'}`);
            httpError.status = errorData.status;
            httpError.data = errorData; // 파싱된 에러 데이터를 포함시킬 수 있습니다.
            throw httpError;
        }

        // eventChannel 생성
        const channel = yield call(eventChannel, (emitter) => {
            // emitter(값) 을 호출하면, saga에서 take(channel)로 받을 수 있음
            // emitter(END)를 호출하면, saga의 while (true) 루프가 빠져나옴

            return streamChatMessages(
                response,
                message => emitter(message),        // onMessage
                error => emitter(new Error(error)) // onError
            );
        });

        try {
            // 채널에서 메시지를 무한히 받아 처리
            while (true) {
                const chatMessage = yield take(channel);

                console.log("chatMessage: ", chatMessage);

                // 'END' 객체가 들어오면 onCompleteCallback과 동일한 로직 처리
                if (chatMessage === END) {
                    console.log("Complete")
                    // ======== onCompleteCallback과 같은 로직 예시 ========
                    yield * handler.onComplete();
                    // 무한 루프 탈출
                    break;
                }

                if(chatMessage?.error) {
                    yield put({
                        type: SEND_MESSAGE_FAIL,
                        error: chatMessage.error.errorMessage,
                    });
                } else {
                    // ======== subscribeCallback과 같은 로직 예시 ========
                    // 스트림으로 받은 메시지를 버퍼에 추가하는 예시
                    yield * handler.subscribe(chatMessage);
                }
            }
        } catch (e) {
            console.error(e);
        } finally {
            channel.close();
        }
    } catch (error) {
        // 에러 처리
        console.error('sendStreamMessage error:', error);
        // 필요 시 에러 액션
        yield put({ type: SEND_MESSAGE_FAIL, error: error.message});
    }
}

export function* watchSendMessage() {
    yield takeLatest(SEND_MESSAGE_REQUEST, sendStreamMessage);
}

export function* StreamMessageSaga() {
    yield all([
        fork(watchSendMessage),
    ]);
}