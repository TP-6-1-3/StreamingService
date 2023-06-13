import { request } from "../../libs/request"

export const AddFriendRequest = (nickname: string) => {
    return request<any>('POST', `/friends/${nickname}`, {})
}