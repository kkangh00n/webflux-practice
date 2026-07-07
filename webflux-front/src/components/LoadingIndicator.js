import React from 'react';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faSpinner} from '@fortawesome/free-solid-svg-icons';
import '../App.css';
import {useSelector} from "react-redux";

const LoadingIndicator = () => {
    const globalLoading = useSelector((state) => state.global.globalLoading);

    return (
        <>
            {
                (globalLoading) &&
                <div
                    className="fixed inset-0 w-full h-full bg-black bg-opacity-50 backdrop-blur-micro flex justify-center items-center z-100">
                    <div className="text-center py-4 text-white">
                        <FontAwesomeIcon icon={faSpinner} className="spinner" size="2x"/>
                    </div>
                </div>
            }
        </>
    )
}

export default LoadingIndicator;