import React, {useEffect, useState} from 'react';
import './markdown.css';
import ReactMarkdown from 'react-markdown'
import remarkGfm from 'remark-gfm';
import {ChevronDown, ChevronUp} from "lucide-react";

const OpponentMessage = ({message, finalMessage}) => {

    const [open, setOpen] = useState(false);

    useEffect(() => {
        if (finalMessage) {
            setOpen(true);
        } else {
            setOpen(false);
        }
    }, [finalMessage]);

    return (
        <div className="flex flex-col w-full max-w-[50rem]">
            <div className="flex justify-between w-full text-sm text-gray-400 ">
                {message?.title ?
                    <div className="flex font-semibold mt-1.5">{message.title}</div>
                    :
                    <div/>
                }
                {
                    message?.title &&
                    <div className="flex cursor-pointer mt-5" onClick={() => setOpen((prev) => !prev)}>
                        {open ?
                            <>
                                <div className="flex font-semibold ">Hide</div>
                                <ChevronUp className="ml-1 pb-1"/>
                            </>
                            :
                            <>
                                <div className="flex font-semibold ">Response</div>
                                <ChevronDown className="ml-1 pb-1"/>
                            </>
                        }
                    </div>
                }
            </div>
            {
                (open || !message?.title) &&
                <div className="flex items-start">
                    <div className={"flex flex-col mt-4"}>
                        <div className="shadow-lg rounded-lg p-6 flex flex-col justify-between bg-zinc-850">
                            <ReactMarkdown
                                className="prose prose-invert prose-hr:border-white prose-p:text-white max-w-[50rem]"
                                remarkPlugins={[remarkGfm]}>
                                {message?.content}
                            </ReactMarkdown>
                        </div>
                    </div>
                </div>
            }
            <div
                className={"transition-all duration-500 ease-in-out border-b-2 border-dashed border-gray-700 mt-3"}></div>
        </div>
    );
};
export default OpponentMessage;