import axios from 'axios';

const BASE_URL = 'http://localhost:8080';

// 스트림 응답을 처리하는 함수
function parseStreamChatMessages(response, onSubscribe, onComplete, onError) {
    console.log(response);
    console.log(response.body);
    const reader = response.body.getReader(); // axios의 response.data는 ReadableStream
    const decoder = new TextDecoder(); // 스트림 데이터 디코딩
    let buffer = '';

    function read() {
        reader.read().then(({ done, value }) => {
            if (done) {
                onComplete();
                console.log("Streaming ended.");
                return;
            }

            buffer += decoder.decode(value, { stream: true });

            let boundary = buffer.lastIndexOf('\n');
            if (boundary !== -1) {
                const chunk = buffer.slice(0, boundary);
                buffer = buffer.slice(boundary + 1);

                const lines = chunk.split('\n');
                let paragraphBuffer = '';
                let outputBuffer = '';

                for (const line of lines) {
                    if (line.trim() === 'data:' || line.trim() === '') {
                        // 문단 또는 줄바꿈 구분
                        if (paragraphBuffer) {
                            outputBuffer += paragraphBuffer + '\n\n';
                            paragraphBuffer = '';
                        }
                    } else {
                        const content = line.replace(/^data:\s*/, '');
                        paragraphBuffer += content;
                    }
                }

                if (outputBuffer.trim()) {
                    onSubscribe(outputBuffer.trim());
                }
            }

            read(); // 다음 청크 읽기
        }).catch(onError);
    }

    read();
}

// 메시지 전송 함수
export async function sendMessageFlux(data, onSubscribe, onComplete, onError, urlSelection) {
    try {

        const response = await fetch(BASE_URL + urlSelection.url, {
        // const response = await fetch(`${BASE_URL}/chat/sinkcontext`, {
            body: data,
            method: urlSelection.method,
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'text/event-stream', // SSE로 스트림 처리
            },
        });

        // 스트림 처리
        parseStreamChatMessages(response, onSubscribe, onComplete, onError);

    } catch (error) {
        console.error("Error sending message:", error);
        onError(error);
    }
}