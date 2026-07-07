import {ADD_MESSAGE_BUFFER, ADD_MESSAGE_HISTORY, CHANGE_MESSAGE_BUFFER} from "../../reducer/MessageReducer";
import {put, select} from "@redux-saga/core/effects";


export const StringMessageHandler = {
    * onRequest(action) {
        yield put({
            type: ADD_MESSAGE_HISTORY,
            data: {
                content: action.data.userInput,
            },
        });
    },
    * subscribe(chatMessage) {
        yield put({
            type: ADD_MESSAGE_BUFFER,
            data: chatMessage,
        });
    },
    * onComplete() {
        const messageBuffer = yield select(
            (state) => state.message.messageBuffer
        );
        // 여기서 messageBuffer를 history로 저장
        yield put({
            type: ADD_MESSAGE_HISTORY,
            data: {content: messageBuffer},
        });
        // 버퍼 초기화
        yield put({
            type: CHANGE_MESSAGE_BUFFER,
            data: "",
        });
    },
}