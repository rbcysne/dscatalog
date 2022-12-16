import { LoginResponse } from "types/LoginResponse";

const token = 'authData';

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