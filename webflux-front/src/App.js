import './App.css';
import './globals.css';
import React, {useEffect, useState} from 'react';
import MessageFooter from "./components/MessageFooter";
import MessageBody from "./components/MessageBody";
import MessageError from "./components/MessageError";
import MessageHeader from "./components/MessageHeader";
import {useDispatch} from "react-redux";
import {loadHomeFacadeAction} from "./reducer/FacadeReducer";
import LoadingIndicator from "./components/LoadingIndicator";

const App = () => {

    const dispatch = useDispatch();

    useEffect(() => {
        dispatch(loadHomeFacadeAction());
    }, []);

    return (
        <div
            style={{fontFamily: 'sans-serif'}}
            className=" bg-zinc-900">
            <MessageHeader />
            <div className="flex bg-zinc-900 flex-col h-full text-gray-700 ">
                <div
                    className={`flex flex-col w-full transition-padding duration-300 ease-in-out`}>
                    <MessageBody />
                    <MessageFooter />
                </div>
            </div>
            <MessageError/>
        </div>
    );
}
export default App;
