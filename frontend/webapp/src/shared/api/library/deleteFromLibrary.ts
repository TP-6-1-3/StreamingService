import { request } from "../../libs/request"


export const DeleteFromLibrary = (songId: number) => {
    return request('DELETE', `/library/${songId}`)
}