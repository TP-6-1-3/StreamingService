import { request } from "../../libs/request"

export interface IFriendsListRequest {
    "paging": {
        "pageNumber": number,
        "size": number,
        "totalPages": number
    },
    "data": [
        {
            "userId": number,
            "email": string,
            "nickname": string
        }
    ]
}

export const GetFriendsRequest = (nickname?: string) => {
    return request<IFriendsListRequest>('GET', '/friends', {
        data: Object.assign({
            size: 20,
        }, nickname ? { nickname } : {})
    })
}