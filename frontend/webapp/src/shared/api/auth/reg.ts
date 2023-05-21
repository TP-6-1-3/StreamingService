import { request } from "../../libs/request"

export interface IRegData {
    firstName: string,
    lastName: string,
    email: string,
    nickname: string,
    password: string,
    repeatPassword: string
}

export const RegRequest = (data: IRegData) => {
    return request('POST', 'auth/register', {
        data
    })
}