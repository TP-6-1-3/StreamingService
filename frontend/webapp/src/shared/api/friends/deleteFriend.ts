import { request } from "../../libs/request"

export const DeleteFriendRequest = (nickname: string) => {
    return request<any>('DELETE', `/friends/${nickname}`, {})
}