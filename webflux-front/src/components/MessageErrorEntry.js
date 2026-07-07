import React from 'react';

const MessageErrorEntry = ({ error, onClearError }) => {
    return (
        <div
            style={{ wordBreak: 'break-word', overflowWrap: 'break-word', whiteSpace: 'pre-wrap' }}
            className="fixed inset-0 z-50 flex items-center justify-center">
            <div className="absolute inset-0 bg-black bg-opacity-60 transition-opacity"></div>
            <div className="bg-white p-8 rounded-lg shadow-2xl mx-auto text-left transform transition-all"
                 style={{ maxWidth: '90vw', maxHeight: '80vh', overflow: 'auto' }}>
                <h2 className="text-xl font-semibold text-gray-900 mb-6">Error Occurred</h2>
                <div className="space-y-4 mb-6 px-4 py-2 bg-gray-50 rounded-md shadow-inner"
                            style={{ height: 'auto', maxHeight: '50vh' }}>
                    <p className="text-red-700 mb-4 break-words">{error}</p>
                </div>
                <button onClick={onClearError} className="bg-indigo-700 text-white font-bold px-6 py-3 rounded-lg hover:bg-indigo-800 focus:outline-none focus:ring-4 focus:ring-indigo-700 focus:ring-opacity-75 shadow-lg transition-all transform hover:-translate-y-1 hover:shadow-2xl">
                    Close
                </button>
            </div>
        </div>
    );
}

export default MessageErrorEntry;
