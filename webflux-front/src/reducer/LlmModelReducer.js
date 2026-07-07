const initialState = {
    modelSelectionList: [],
    selectedModel: {
        displayName:"Select Model"
    },
};

export const CHANGE_MODEL_SELECTION = 'CHANGE_MODEL_SELECTION';
export const ADD_MODEL_SELECTION = 'ADD_MODEL_SELECTION';

export const changeModelSelectionAction = (data) => ({
    type: CHANGE_MODEL_SELECTION,
    data
})

const LlmModelReducer = (state = initialState, action) => {
    switch (action.type) {
        case CHANGE_MODEL_SELECTION: {
            return {
                ...state,
                selectedModel: action.data
            };
        }
        case ADD_MODEL_SELECTION: {
            return {
                ...state,
                modelSelectionList: action.data,
                selectedModel: action.data[0]
            };
        }

        default:
            return state;
    }
}


export default LlmModelReducer;