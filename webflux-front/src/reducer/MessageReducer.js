const initialState = {
    messageHistory: [],
    messageBuffer: "",
};

export const ADD_MESSAGE_BUFFER = 'ADD_MESSAGE_BUFFER';

export const CHANGE_MESSAGE_BUFFER = 'CHANGE_MESSAGE_BUFFER';

export const ADD_MESSAGE_HISTORY = 'ADD_MESSAGE_HISTORY';

export const SEND_MESSAGE_REQUEST = 'SEND_MESSAGE_REQUEST';
export const SEND_MESSAGE_FAIL = 'SEND_MESSAGE_FAIL';
export const SEND_MESSAGE_SUCCESS = 'SEND_MESSAGE_SUCCESS';

export const sendMessageAction = (data) => ({
    type: SEND_MESSAGE_REQUEST,
    data
});


const MessageReducer = (state = initialState, action) => {
    switch (action.type) {
        case ADD_MESSAGE_BUFFER: {
            return {
                ...state,
                messageBuffer: state.messageBuffer + action.data
            };
        }
        case CHANGE_MESSAGE_BUFFER:
            return {
                ...state,
                messageBuffer: action.data
            };
        case ADD_MESSAGE_HISTORY:
            return {
                ...state,
                messageHistory: [...state.messageHistory, action.data],
            };
        default:
            return state;
    }
}


export default MessageReducer;