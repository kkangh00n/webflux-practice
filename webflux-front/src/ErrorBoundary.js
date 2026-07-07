import React from 'react';

class ErrorBoundary extends React.Component {
    constructor(props) {
        super(props);
        this.state = { hasError: false };
    }

    static getDerivedStateFromError(error) {
        // 에러 발생 시, 상태를 업데이트하여 UI를 변경할 수 있습니다.
        return { hasError: true };
    }

    componentDidCatch(error, errorInfo) {
        console.error("An error occurred:", error, errorInfo);
    }

    handleClearAndReload = () => {
        window.location.href = "/";
    };

    render() {
        if (this.state.hasError) {
            // 에러 발생 시 렌더링할 대체 UI
            return (
                <div className="flex flex-col min-h-screen items-center h-full bg-zinc-900 text-gray-700 w-full transition-padding duration-300 ease-in-out">
                    <div className="flex flex-col items-center justify-center flex-grow">
                        <div className="border-2 p-10 rounded w-full max-w-[90rem] mt-5 text-white">
                            <h1 className="text-3xl mb-4">Something went wrong. <br /> Please reload the page by clicking the
                                button below.</h1>
                            <button
                                onClick={this.handleClearAndReload}
                                className="mt-4 px-4 py-2 bg-indigo-500 text-white rounded hover:bg-blue-700 transition-colors duration-300"
                            >
                                Clear Cache and Reload
                            </button>
                        </div>
                    </div>
                </div>
            );
        }

        return this.props.children; // 에러가 없으면 자식 컴포넌트를 정상 렌더링
    }
}

export default ErrorBoundary;
