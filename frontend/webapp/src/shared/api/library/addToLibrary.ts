import { request } from "../../libs/request"


export const AddToLibraryRequest = (songId: number) => {
    return request('POST', `/library/${songId}`)
}