import React, {useState} from 'react';
import {SendIcon} from "lucide-react";
import {sendMessageAction} from "../reducer/MessageReducer";
import {useDispatch, useSelector} from "react-redux";

const MessageFooter = () => {
    const [inputMessage, setInputMessage] = useState('');
    const urlSelection = useSelector((state) => state.urlSelection.urlSelection);
    const selectedModel = useSelector((state) => state.llmModel.selectedModel);
    const dispatch = useDispatch();

    const handleInputChange = (e) => {
        setInputMessage(e.target.value);
    };


    const handleKeyPress = (e) => {
        if (e.key === 'Enter' && !e.shiftKey) { // Shift+Enter를 누를 경우 줄바꿈 처리를 위해
            e.preventDefault();
            handleSendStringMessage(inputMessage, urlSelection);
        }
    };

    const handleSendStringMessage = (userInput, urlSelection) => {
        console.log("send");
        dispatch(sendMessageAction({
            userInput: userInput,
            urlSelection: urlSelection,
            selectedModel: selectedModel
        }));
        setInputMessage("");
    }

    return (
        <footer
            className="sticky bottom-0 flex justify-center z-20 p-6 w-full shadow bg-zinc-900"
        >
            <div className="flex gap-4 w-[60rem]">
                <textarea
                    value={inputMessage}
                    onChange={handleInputChange}
                    onKeyPress={handleKeyPress}
                    className="flex-1 text-lg text-zinc-300 px-4 py-2 rounded-3xl border-none focus:ring-2 focus:ring-indigo-300 resize-none bg-zinc-850"
                    placeholder="Type message"/>
                <>
                    <div
                        className="text-center mt-1 text-white cursor-pointer transform hover:-translate-y-1 transition-all duration-200"
                        onClick={() => {
                            handleSendStringMessage(inputMessage, urlSelection);
                        }}>
                        <SendIcon className=" w-[1.5rem] h-[1.5rem] opacity-70 mt-1"
                             alt="send"/>
                    </div>
                </>
            </div>
        </footer>
    );
};

export default MessageFooter;