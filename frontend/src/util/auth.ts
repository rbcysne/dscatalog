import jwtDecode from "jwt-decode";
import { Role } from "types/Role";
import { TokenData } from "types/TokenData";
import { getAuthData } from "./storage";

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