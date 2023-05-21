import { createEvent, createStore } from "effector";

export interface IUserCredentials {
    accessToken: string;
    refreshToken: string;
}

export const $userCredentials = createStore<IUserCredentials | null>(null);
export const setUserCredentialsFx = createEvent<IUserCredentials>();
$userCredentials.on(setUserCredentialsFx, (_, credentials: IUserCredentials) => credentials);