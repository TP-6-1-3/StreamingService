import { request } from "../../libs/request"
import { IUserCredentials } from "../../stores/user"

export const AuthRequest = (email: string, password: string) => {
    return request<IUserCredentials>('POST', '/auth/login', {
            data: {
                email,
                password
            }
        })
}