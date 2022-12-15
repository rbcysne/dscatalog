import { createContext } from "react";
import { AuthContextType } from "types/AuthContextType";

export const AuthContext = createContext<AuthContextType>({
    authContextData: {
        authenticated: false
    },
    setAuthContextData: () => null
})