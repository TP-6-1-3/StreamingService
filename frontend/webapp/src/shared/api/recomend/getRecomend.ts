import {request} from "../../libs/request";
import {IGetSongsFilter, ISongsList} from "../songs/getSongs";

export const GetRecomend = () => {

    return request<any>('GET', `/recommendations`);
}