import {
    ADD_MESSAGE_BUFFER,
    ADD_MESSAGE_HISTORY,
    CHANGE_MESSAGE_BUFFER,
    SEND_MESSAGE_SUCCESS
} from "../../reducer/MessageReducer";
import {put, select} from "@redux-saga/core/effects";


export const ChainMessageHandler = {
    * onRequest(action) {
        yield put({
            type: ADD_MESSAGE_HISTORY,
            data: {
                title: "User Input",
                content: action.data.userInput,
            },
        });
    },
    * subscribe(chatMessage) {
        yield put({
            type: ADD_MESSAGE_HISTORY,
            data: {
                content: chatMessage.response,
                title: chatMessage.title
            },
        });
    },
    * onComplete() {
        yield put({
            type: SEND_MESSAGE_SUCCESS,
            data: "",
        });
    }
}