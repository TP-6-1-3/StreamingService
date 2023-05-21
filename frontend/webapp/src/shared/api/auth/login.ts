import { request } from "../../libs/request"

export const AuthRequest = (email: string, password: string) => {
    return request('POST', 'auth/login', {
            data: {
                email,
                password
            }
        })
}