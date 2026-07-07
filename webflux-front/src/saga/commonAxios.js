import axios from 'axios';

export const BASE_URL = process.env.REACT_APP_PROFILE ? 'http://localhost:8080' : 'http://localhost:8080';
// Axios 인스턴스 생성
const commonAxios = axios.create({
    baseURL: BASE_URL,
    withCredentials: false,
});

export default commonAxios;