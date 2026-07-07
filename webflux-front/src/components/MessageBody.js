import React, {useEffect, useRef, useState} from 'react';
import OpponentMessage from "./message/OpponentMessage";
import {useSelector} from "react-redux";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faSpinner} from "@fortawesome/free-solid-svg-icons";

const MessageBody = () => {

    const messageHistory = useSelector((state) => state.message.messageHistory);
    const messageBuffer = useSelector((state) => state.message.messageBuffer);
    const globalLoading = useSelector((state) => state.global.globalLoading);

    return (
        <div className="w-full min-h-[70rem] flex flex-col items-center ">
            {
                messageHistory?.map((message, index) => (
                    <OpponentMessage
                        finalMessage={messageHistory?.length === index + 1}
                        key={index}
                        message={message}
                    />
                ))
            }
            {
                messageBuffer &&
                <OpponentMessage
                    finalMessage={true}
                    message={{
                        content: messageBuffer,
                    }}
                />
            }
            {
                globalLoading &&
                <div className=" text-center py-4 text-white flex">
                    <div className="flex text-center mt-10 ml-1 text-indigo-200">
                        <div className="flex animation-message">
                            <FontAwesomeIcon icon={faSpinner} className="ml-2 mt-1 spinner"/>
                        </div>
                    </div>
                </div>
            }
        </div>
    );
};

export default MessageBody;