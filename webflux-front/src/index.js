import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import store from "./store/store";
import {Provider} from "react-redux";
import ErrorBoundary from "./ErrorBoundary";


const rootStore = store();
const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <ErrorBoundary>
        <Provider store={rootStore}>
            <App/>
        </Provider>
    </ErrorBoundary>
);