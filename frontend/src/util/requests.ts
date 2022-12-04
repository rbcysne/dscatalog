
import axios from 'axios';
import qs from 'qs';
import { LoginData } from "types/LoginData";

export const BASE_URL = process.env.REACT_APP_BACKEND_URL ?? 'http://localhost:8080';

const CLIENT_ID = process.env.REACT_APP_CLIENT_ID ?? 'test-client-id';
const CLIENT_SECRET = process.env.REACT_APP_CLIENT_SECRET ?? 'test1234';


export const requestBackendLogin = (loginData : LoginData) => {

    const headers = {
        'Content-Type': 'application/x-www-form-urlencoded',
        Authorization: 'Basic ' + window.btoa(CLIENT_ID + ':' + CLIENT_SECRET)
    }

    const data = qs.stringify({
        ...loginData,
        grant_type : 'password'
    });

    return axios({method: 'POST', baseURL: BASE_URL, url: '/oauth/token', data, headers});

}