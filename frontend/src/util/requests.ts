import axios, { AxiosRequestConfig } from 'axios';
import qs from 'qs';
import jwtDecode from 'jwt-decode';

import { LoginData } from 'types/LoginData';
import { LoginResponse } from 'types/LoginResponse';
import { TokenData } from 'types/TokenData';
import history from './history';
import { Role } from 'types/Role';

export const BASE_URL =
    process.env.REACT_APP_BACKEND_URL ?? 'http://localhost:8080';

const CLIENT_ID = process.env.REACT_APP_CLIENT_ID ?? 'test-client-id';
const CLIENT_SECRET = process.env.REACT_APP_CLIENT_SECRET ?? 'test1234';

const token = 'authData';

export const requestBackendLogin = (loginData: LoginData) => {
    const headers = {
        'Content-Type': 'application/x-www-form-urlencoded',
        Authorization: 'Basic ' + window.btoa(CLIENT_ID + ':' + CLIENT_SECRET),
    };

    const data = qs.stringify({
        ...loginData,
        grant_type: 'password',
    });

    return axios({
        method: 'POST',
        baseURL: BASE_URL,
        url: '/oauth/token',
        data,
        headers,
    });
};

export const requestBackend = (config: AxiosRequestConfig) => {
    const headers = config.withCredentials
        ? {
              ...config.headers,
              Authorization: 'Bearer ' + getAuthData().access_token,
          }
        : config.headers;

    return axios({ ...config, baseURL: BASE_URL, headers });
};

export const saveAuthData = (obj: LoginResponse) => {
    localStorage.setItem(token, JSON.stringify(obj));
};

export const getAuthData = () => {
    const str = localStorage.getItem(token) ?? '{}';
    return JSON.parse(str) as LoginResponse;
};

export const removeAuthData = () => {
  localStorage.removeItem(token);
}

export const getTokenData = () : TokenData | undefined => {

  try {
    return jwtDecode(getAuthData().access_token) as TokenData;
  } catch(error) {
    return undefined;
  }
}

export const isAuthenticated = () : boolean => {
  
  const tokenData = getTokenData();

  let authenticated = false;

  if(tokenData !== undefined) {
    authenticated = (tokenData && (tokenData.exp * 1000) > Date.now()) ? true : false;
  }

  return authenticated;
}

export const hasAnyRole = (roles: Role[]) : boolean => {

  if(roles.length === 0) {
    return true;
  }

  const tokenData = getTokenData();

  if(tokenData !== undefined) {
    return roles.some(role => tokenData.authorities.includes(role));
  }

  return false;
}

// Add a request interceptor
axios.interceptors.request.use(
    function (config) {
      // Do something before request is sent

      return config;
    },
    function (error) {
      // Do something with request error

      return Promise.reject(error);
    }
);

// Add a response interceptor
axios.interceptors.response.use(
    function (response) {
      // Any status code that lie within the range of 2xx cause this function to trigger
      // Do something with response data

      return response;
    },
    function (error) {
      // Any status codes that falls outside the range of 2xx cause this function to trigger
      // Do something with response error
      
      if(error.response.status === 401) {
        history.push('/admin/auth');
      }

      return Promise.reject(error);
    }
);

