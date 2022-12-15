import { TokenData } from "./TokenData";

export type AuthContextData = {
    authenticated: boolean;
    tokenData?: TokenData;
}