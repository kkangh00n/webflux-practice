import {all,fork} from 'redux-saga/effects';
import {StreamMessageSaga} from "./StreamMessageSaga";
import {FacadeSaga} from "./FacadeSaga";

export default function* rootSaga(){
    yield all([
        fork(StreamMessageSaga),
        fork(FacadeSaga),
    ]);
}