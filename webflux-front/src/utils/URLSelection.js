import {OneShotMessageHandler} from "../saga/handler/OneShotMessageHandler";
import {ChainMessageHandler} from "../saga/handler/ChainMessageHandler";

export const URL_SELECTION_LIST = [{
    method: "POST",
    url: "/chat/oneshot",
    label: "One shot",
    handler: OneShotMessageHandler,
},{
    method: "POST",
    url: "/chat/oneshot/stream",
    label: "One shot Stream",
    handler: OneShotMessageHandler,
},{
    method: "POST",
    url: "/chat/cot",
    label: "Chain of Thought",
    handler: ChainMessageHandler,
}]

