import React from 'react';
import UrlDropDown from "./dropdown/UrlDropDown";
import LlmModelDropDown from "./dropdown/LlmModelDropDown";


const MessageHeader = () => {

    return (
        <header
            className="sticky w-full top-0 z-100 flex items-center bg-zinc-880 justify-between p-4 border-b-2 border-zinc-850 text-white ">
            <div/>
            <div className="flex flex-row">
                <div className="flex flex-row items-center animation-message">
                    <img loading="lazy"
                         src="/layout/whitelogo.png"
                         alt="" className="w-5 aspect-square"/>
                    <div className="text-2xl font-semibold  text-white ml-1 pt-1">
                        Your-App
                    </div>
                </div>
            </div>
            <div>
                <LlmModelDropDown/>
                <UrlDropDown/>
            </div>
        </header>
    );
};
export default MessageHeader;