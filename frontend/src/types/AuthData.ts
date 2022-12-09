import { TokenData } from "./TokenData";

export type AuthData = {
    authenticated: boolean;
    tokenData?: TokenData;
}