import {request} from "../../libs/request";
import {IGetSongsFilter, ISongsList} from "../songs/getSongs";


export const GetSongsFromLibraryRequest = (params: IGetSongsFilter) => {
    const paramsArray: any[] = Object.keys(params);
    const assignParams = paramsArray.map<any>((item: keyof IGetSongsFilter) => `${item}=${params[item]}`).join('&');
    const query = paramsArray.length ? `?${assignParams}` : '';

    return request<ISongsList>('GET', `/library${query}`);
}