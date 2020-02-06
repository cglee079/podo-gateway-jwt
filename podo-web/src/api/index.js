import axios from 'axios';

const server = "http://localhost:18080";

function join(data) {
    return axios.post(`${server}/join`, data);
}

function login(data) {
    return axios.post(`${server}/login`, data);
}

function test(headers) {
    return axios.get(`${server}/test`, {headers : headers});
}

export {join, login, test}
