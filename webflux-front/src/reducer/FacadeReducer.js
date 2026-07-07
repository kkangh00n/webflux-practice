import {CHANGE_MODEL_SELECTION} from "./LlmModelReducer";

const initialState = {
};

export const LOAD_HOME_FACADE_FAILURE = 'LOAD_HOME_FACADE_FAILURE';
export const LOAD_HOME_FACADE_REQUEST = 'LOAD_HOME_FACADE_REQUEST';
export const LOAD_HOME_FACADE_SUCCESS = 'LOAD_HOME_FACADE_SUCCESS';

export const loadHomeFacadeAction = (data) => ({
    type: LOAD_HOME_FACADE_REQUEST,
    data
});


const FacadeReducer = (state = initialState, action) => {
    switch (action.type) {
        case LOAD_HOME_FACADE_REQUEST: {
            return {
                ...state,
            };
        }
        case LOAD_HOME_FACADE_SUCCESS: {
            return {
                ...state,
            };
        }

        default:
            return state;
    }
}


export default FacadeReducer;