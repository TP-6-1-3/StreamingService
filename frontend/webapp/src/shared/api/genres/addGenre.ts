import { request } from "../../libs/request"

interface IGenresFields {
    name: string
}
export const AddGenreRequest = (genre: IGenresFields) => {
    return request('POST', `/genres`, {data: genre})
}