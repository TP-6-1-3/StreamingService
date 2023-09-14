import {request} from "../../libs/request";

export interface IGenre {
    genreId: number,
    name: string,
}


export const GetGenresRequest = () => {
    return request<IGenre[]>('GET', `/genres`);
}