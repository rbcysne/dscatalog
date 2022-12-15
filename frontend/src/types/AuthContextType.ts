import { AuthContextData } from "./AuthContextData"

export type AuthContextType = {
    authContextData: AuthContextData;
    setAuthContextData: (authContextData: AuthContextData) => void;
}

