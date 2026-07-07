import {combineReducers} from "redux";
import LlmModelReducer from "./LlmModelReducer";
import FacadeReducer from "./FacadeReducer";
import MessageReducer from "./MessageReducer";
import UrlSelectionReducer from "./UrlSelectionReducer";
import GlobalReducer from "./GlobalReducer";

const RootReducer = combineReducers({
    message: MessageReducer,
    urlSelection: UrlSelectionReducer,
    llmModel: LlmModelReducer,
    facade: FacadeReducer,
    global: GlobalReducer,
});

export default RootReducer;