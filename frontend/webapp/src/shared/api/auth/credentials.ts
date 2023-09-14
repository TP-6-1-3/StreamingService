import { AxiosResponse } from "axios"
import { request } from "../../libs/request"
import { IProfileCredentials } from "../../stores/user"

export const GetCredentialsRequest = () => {
    return request<IProfileCredentials>('GET', '/auth/credentials', {})
}