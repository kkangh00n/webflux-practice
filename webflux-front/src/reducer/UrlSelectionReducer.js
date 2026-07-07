import {URL_SELECTION_LIST} from "../utils/URLSelection";

const initialState = {
    urlSelection: URL_SELECTION_LIST[0],
};

export const CHANGE_URL_SELECTION = 'CHANGE_URL_SELECTION';

export const changeUrlSelectionAction = (data) => ({
    type: CHANGE_URL_SELECTION,
    data
})

const UrlSelectionReducer = (state = initialState, action) => {
    switch (action.type) {
        case CHANGE_URL_SELECTION: {
            return {
                ...state,
                urlSelection: action.data
            };
        }

        default:
            return state;
    }
}


export default UrlSelectionReducer;