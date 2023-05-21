import { request } from "../../libs/request"

export const VerifyCodeRequest = (code: string) => {
    return request('POST', `auth/verify/${code}`)
}