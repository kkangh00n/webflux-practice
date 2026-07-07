import React, {useEffect, useState} from 'react';
import MessageErrorEntry from "./MessageErrorEntry";
import {useDispatch, useSelector} from "react-redux";
import {clearGlobalError} from "../reducer/GlobalReducer";

const MessageError = () => {
    const dispatch = useDispatch();
    const globalError = useSelector((state) => state.global.globalError);

    const getError = () => {
        switch (globalError?.errorCode) {
            case 20:
                return<>
                    <div className="mb-10">{globalError.errorMessage}</div>
                </>
            default:
                return JSON.stringify(globalError);

        }
    }

    return (
        <>
            {globalError && (<MessageErrorEntry error={getError()} onClearError={() => dispatch(clearGlobalError())}/>)}
        </>
    );
};

export default MessageError;