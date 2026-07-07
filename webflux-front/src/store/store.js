import {applyMiddleware, createStore} from "redux";
import RootReducer from "../reducer/RootReducer";
import createSagaMiddleware from 'redux-saga';
import rootSaga from "../saga/RootSaga";


const Store = ()=>{
    const sagaMiddleware = createSagaMiddleware();
    const middlewares = [sagaMiddleware];
    const enhancer = applyMiddleware(...middlewares);

    const store = createStore(RootReducer, enhancer);
    store.sagaTask = sagaMiddleware.run(rootSaga);
    return store;
};


export default Store;