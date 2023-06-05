import { request } from "../../libs/request"

export interface IGetSongsFilter {
    count?: number,
    pageNumber?: number,
    title?: string,
    sort?: 'BY_TITLE' | 'BY_DURATION',
    isAsc?: boolean,
    genres?: number[],
    singer?: number
}

export interface ISong {
    songId: number,
    title: string,
    duration: string,
    url?: string,
    singer: {
        singerId: number,
        fullName: string,
        description: string
    },
    genres: [
        {
            genreId: number,
            name: string
        }
    ]
}

export interface ISongsList {
    paging: {
        pageNumber: number,
        size: number,
        totalPages: number
    },
    data: ISong[]
}

export const GetSongsRequest = (params: IGetSongsFilter) => {
    const paramsArray: any[] = Object.keys(params);
    const assignParams = paramsArray.map<any>((item: keyof IGetSongsFilter) => `${item}=${params[item]}`).join('&');
    const query = paramsArray.length ? `?${assignParams}` : '';

    return request<ISongsList>('GET', `/songs${query}`);
}