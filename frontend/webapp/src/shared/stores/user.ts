import { createEvent, createStore } from "effector";

export interface IUserCredentials {
    accessToken: string;
    refreshToken: string;
}

export interface IUserCredentialsRole {
    "roleId": number,
    "name": string,
    "authority": string
}

export interface IProfileCredentials {
    "userId": number,
    "firstName": string,
    "lastName": string,
    "email": string,
    "nickname": string,
    "isVerified": boolean,
    "roles": IUserCredentialsRole[]
}

export const $userCredentials = createStore<IUserCredentials | null>(null);
export const setUserCredentialsFx = createEvent<IUserCredentials>();
$userCredentials.on(setUserCredentialsFx, (_, credentials: IUserCredentials) => credentials);

export const $userData = createStore<IProfileCredentials | null>(null);
export const setUserDataFx = createEvent<IProfileCredentials>();
$userData.on(setUserDataFx, (_, profileCredentials: IProfileCredentials) => profileCredentials);