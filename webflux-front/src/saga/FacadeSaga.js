import {all, fork, take, put, call} from 'redux-saga/effects';
import {cancelled, select, takeLatest} from "@redux-saga/core/effects";
import commonAxios from "./commonAxios";
import {LOAD_HOME_FACADE_FAILURE, LOAD_HOME_FACADE_REQUEST, LOAD_HOME_FACADE_SUCCESS} from "../reducer/FacadeReducer";
import {ADD_MODEL_SELECTION} from "../reducer/LlmModelReducer";


function loadHomeFacadeAPI(data) {
    let url = '/facade/home';
    return commonAxios.post(url, data);
}

function* loadHomeFacade(action) {
    try {
        const result = yield call(loadHomeFacadeAPI, action.data);
        yield put({
            type: ADD_MODEL_SELECTION,
            data: result.data.availableModelList,
        });
    } catch (err) {
        yield put({
            type: LOAD_HOME_FACADE_FAILURE,
            error: err.response.data.error,
        })
    }
}

export function* watchLoadHomeFacade() {
    yield takeLatest(LOAD_HOME_FACADE_REQUEST, loadHomeFacade);
}

export function* FacadeSaga() {
    yield all([
        fork(watchLoadHomeFacade),
    ]);
}