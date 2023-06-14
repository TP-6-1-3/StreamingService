import { request } from "../../libs/request"


export const AddRecomendation = (nickname: string, songId: number) => {
    return request('POST', `/recommendations/${nickname}/recommend/${songId}`)
}