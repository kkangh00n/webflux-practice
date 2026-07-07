import MessageReducer, {SEND_MESSAGE_FAIL, SEND_MESSAGE_REQUEST, SEND_MESSAGE_SUCCESS} from "./MessageReducer";

const initialState = {
    globalError: null,
    globalLoading: false,
};

export const GLOBAL_ERROR_CLEAR = 'GLOBAL_ERROR_CLEAR';
export const SET_GLOBAL_ERROR = 'SET_GLOBAL_ERROR';


export const setGlobalError = (data) => ({
    type: SET_GLOBAL_ERROR,
    data
});

export const clearGlobalError = () => ({
    type: GLOBAL_ERROR_CLEAR,
});

const GlobalReducer = (state = initialState, action) => {
    switch (action.type) {
        case SEND_MESSAGE_FAIL:
            console.log(action.error);
            return {
                ...state,
                globalError: action.error,
                globalLoading: false,
            }

        case SEND_MESSAGE_REQUEST:
            return {
                ...state,
                globalLoading: true,
            }

        case SEND_MESSAGE_SUCCESS:
            return {
                ...state,
                globalLoading: false,
            }

        case GLOBAL_ERROR_CLEAR:
            return {
                ...state,
                globalError: null,
                globalLoading: false,
            }
        default:
            return state;
    }
}

export default GlobalReducer;