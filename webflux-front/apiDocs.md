# WebFlux LLM APP API 문서

---
## Facade API

* ### 홈 화면 Facade API (`/facade/home`)
홈 진입 시 필요한 정보를 모아서 내려주는 API입니다.

**1. 엔드포인트 개요**

| 항목        | 내용             | 설명                                 |
| :---------- |:---------------| :----------------------------------- |
| **Endpoint**| `/facade/home` | 홈 화면 구성에 필요한 데이터를 조회 API |
| **Method** | `POST`         |                          |


**2. 요청 (Request)**

* 요청 파라미터 없음

**3. 응답 (Response)**

**3.1. 응답 기본 정보**

| 항목             | 내용                |
| :--------------- | :------------------ |
| **Success Code** | `200 OK`            |
| **Content-Type** | `application/json`  |

**3.2. 응답 본문 (Response Body) 형식:**

    {
      "availableModelList": [
        {
          "displayName": "string",
          "codeName": "string"
        }
      ]
    }

**3.3. 응답 필드 상세**

| 필드명               | 데이터 타입                       | 설명                     |
| :------------------- |:-----------------------------| :----------------------- |
| `availableModelList` | `List<FacadeAvailableModel>` | 사용 가능한 모델 리스트 |

**3.4. `FacadeAvailableModel` 객체 구조**

| 필드명          | 데이터 타입 | 설명           |
| :------------- | :---------- |:-------------|
| `displayName`  | `String`    | 사용자에게 보여줄 이름 |
| `codeName`     | `String`    | 서버 내부 모델 코드  |



**4. 예시**

* **Request:**

        POST /facade/home
        Accept: application/json

* **Response (Success):**

        HTTP/1.1 200 OK
        Content-Type: application/json

        {
          "availableModelList": [
            {
              "displayName": "GPT_4O",
              "codeName": "gpt-4o"
            },
            {
              "displayName": "GEMINI_2_0_FLASH",
              "codeName": "gemini-2.0-flash"
            }
          ]
        }


---
## LLM Chat API

* ### OneShot LLM 채팅 API (`/chat/oneshot`)
사용자의 단일 메시지 요청을 받아 LLM 모델을 통해 응답을 생성하는 API입니다.

**1. 엔드포인트 개요**

| 항목        | 내용                | 설명                                          |
| :---------- | :------------------ | :-------------------------------------------- |
| **Endpoint**| `/oneshot`          | 단일 채팅 요청 처리 API                         |
| **Method** | `POST`              | 새로운 채팅 요청 생성 및 응답 반환              |



**2. 요청 (Request)**

**2.1. 요청 기본 정보**

| 항목             | 내용                |
| :--------------- | :------------------ |
| **Content-Type** | `application/json`  |

**2.2. 요청 본문 (Request Body)**

요청 데이터는 Body에 포함된 `UserChatRequestDto` 형식의 JSON 객체여야 합니다.

    {
      "request": "string",
      "llmModel": "string" 
    }

**2.3. 요청 본문 필드 상세**

| 필드명      | 데이터 타입         | 필수 | 설명                  | 예시             |
| :---------- | :---------------- | :--- | :-------------------- | :--------------- |
| `request`   | `String`          | Yes  | 사용자의 채팅 메시지    | `"오늘 날씨 어때?"` |
| `llmModel`  | `String` (또는 Enum) | Yes  | 사용할 LLM 모델 코드  | `"gpt-4o"`  |




**3. 응답 (Response)**

**3.1. 응답 기본 정보**

| 항목             | 내용                |
| :--------------- | :------------------ |
| **Success Code** | `200 OK`            |
| **Content-Type** | `application/json`  |

**3.2. 응답 본문 형식:**

    {
      "response": "string"
    }

**3.3. 응답 필드 상세**

| 필드명      | 데이터 타입 | 설명               |
| :---------- | :---------- | :----------------- |
| `response`  | `String`    | LLM의 응답 메시지   |



**4. 예시**

* **Request:**

        POST /oneshot HTTP/1.1
        Host: your-api-domain.com
        Content-Type: application/json

        {
          "request": "오늘 서울 날씨 알려줘",
          "llmModel": "gpt-4o"
        }

* **Response (Success):**

        HTTP/1.1 200 OK
        Content-Type: application/json

        {
          "response": "오늘 서울은 대체로 맑으며, 최고 기온은 25도, 최저 기온은 15도로 예상됩니다."
        }

---
* ### OneShot Stream LLM 채팅 API (`POST /chat/oneshot/stream`)

사용자의 단일 메시지 요청을 받아 LLM 모델을 통해 응답을 스트림 형태로 실시간 전송하는 API입니다. Server-Sent Events (SSE) 프로토콜을 사용합니다.

**1. 엔드포인트 개요**

| 항목        | 내용                    | 설명                                                     |
| :---------- |:----------------------| :------------------------------------------------------- |
| **Endpoint**| `/chat/oneshot/stream` | 단일 채팅 요청에 대한 스트리밍 응답 API                     |
| **Method** | `POST`                |                   |



**2. 요청 (Request)**

**2.1. 요청 기본 정보**

| 항목               | 내용                |
|:-----------------| :------------------ |
| **Content-Type** | `application/json`  |
| **Accept**       | `text/event-stream`  |

**2.2. 요청 본문 (Request Body)**

요청 본문은 `UserChatRequestDto` 형식의 JSON 객체여야 합니다. (`/chat/oneshot` API와 동일한 DTO 사용)

    {
      "request": "string",
      "llmModel": "string"
    }

**2.3. 요청 본문 필드 상세**

| 필드명      | 데이터 타입         | 필수 | 설명                  | 예시             |
| :---------- | :---------------- | :--- | :-------------------- | :--------------- |
| `request`   | `String`          | Yes  | 사용자의 채팅 메시지    | `"오늘 날씨 어때?"` |
| `llmModel`  | `String` | Yes  | 사용할 LLM 모델 코드  | `"gpt-4o"`  |



**3. 응답 (Response)**

**3.1. 응답 기본 정보**

| 항목             | 내용                  |
| :--------------- | :-------------------- |
| **Success Code** | `200 OK`              |
| **Content-Type** | `text/event-stream`   |

**3.2. 응답 본문 (Response Body) 스트림 형식:**

응답은 Server-Sent Events (SSE) 스트림입니다. 각 이벤트는 `data:` 필드에 `UserChatResponseDto` 형식의 JSON 객체를 담아 전송합니다. 각 이벤트는 두 개의 개행 문자(`\n\n`)로 구분됩니다.

    data: {"response": "응답 메시지 조각 1"}

    data: {"response": "응답 메시지 조각 2"}
    ...

**3.3. 각 이벤트 데이터(`UserChatResponseDto`) 필드 상세**

| 필드명      | 데이터 타입 | 설명                       |
| :---------- | :---------- | :------------------------- |
| `response`  | `String`    | LLM의 응답 메시지 조각 또는 전체 |




**4. 예시 (Example)**

* **Request:**

        POST /chat/oneshot/stream HTTP/1.1
        Content-Type: application/json
        Accept: text/event-stream

        {
          "request": "안녕!",
          "llmModel": "gpt-4o"
        }

* **Response (Success - Stream):**

        HTTP/1.1 200 OK
        Content-Type: text/event-stream
        Cache-Control: no-cache
        Connection: keep-alive

        data: {"response":"안녕하세요!\n "}

        data: {"response":"반갑습니다.\n "}

---

* ### Chain of Thought LLM 채팅 API (`POST /chat/cot`)

사용자의 단일 메시지 요청을 받아서 응답을 하기 위해 단계별로 분석하고 최종 답변을 생성합니다.   
stream응답이 필요하며 각 stream 데이터는 토큰 조각이 아닌 완성된 메시지를 전달해야합니다.

**1. 엔드포인트 개요**

| 항목        | 내용                | 설명                                                  |
| :---------- | :------------------ |:----------------------------------------------------|
| **Endpoint**| `/chat/cot`   | 채팅 요청에 대한 스트리밍 응답 API                               |
| **Method** | `POST`              |                          |



**2. 요청 (Request)**

**2.1. 요청 기본 정보**

| 항목             | 내용                |
| :--------------- | :------------------ |
| **Content-Type** | `application/json`  |
| **Accept**       | `text/event-stream`  |


**2.2. 요청 본문 (Request Body)**

요청 본문은 `UserChatRequestDto` 형식의 JSON 객체여야 합니다.

    {
      "request": "string",
      "llmModel": "string"
    }

**2.3. 요청 본문 필드 상세**

| 필드명      | 데이터 타입         | 필수 | 설명                  | 예시             |
| :---------- | :---------------- | :--- | :-------------------- |:---------------|
| `request`   | `String`          | Yes  | 사용자의 채팅 메시지    | `"밥 맛있게 먹는 법"` |
| `llmModel`  | `String` | Yes  | 사용할 LLM 모델 코드  | `"gpt-4o"`     |



**3. 응답 (Response)**

**3.1. 응답 기본 정보**

| 항목             | 내용                  |
| :--------------- | :-------------------- |
| **Success Code** | `200 OK`              |
| **Content-Type** | `text/event-stream`   |

**3.2. 응답 본문 (Response Body) 스트림 형식:**

응답은 Server-Sent Events (SSE) 스트림입니다. 각 이벤트는 `data:` 필드에 `UserChatResponseDto` 형식의 JSON 객체를 담아 전송합니다. 각 이벤트는 두 개의 개행 문자(`\n\n`)로 구분됩니다.

    data: {"title": "단계별 분석", "response": "완성된 응답 메시지 - 분석 단계 1"}

    data: {"title": "단계별 분석", "response": "완성된 응답 메시지 - 분석 단계 2"}
    ...

**3.3. 각 이벤트 데이터(`UserChatResponseDto`) 필드 상세**

| 필드명        | 데이터 타입 | 설명                |
|:-----------| :---------- |:------------------|
| `title`    | `String`    | LLM의 응답 메시지 제목    |
| `response` | `String`    | LLM의 응답 메시지 본문 전체 |




**4. 예시 (Example)**

* **Request:**

        POST /oneshot/stream HTTP/1.1
        Content-Type: application/json
        Accept: text/event-stream

        {
          "request": "안녕!",
          "llmModel": "gpt-4o"
        }

* **Response (Success - Stream):**

        HTTP/1.1 200 OK
        Content-Type: text/event-stream
        Cache-Control: no-cache
        Connection: keep-alive

        data: {"title": "단계별 분석", "response": "완성된 응답 메시지 - 분석 단계 1"}

        data: {"title": "단계별 분석", "response": "완성된 응답 메시지 - 분석 단계 2"}

---
