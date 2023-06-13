import {fileRequest} from "../../libs/fileRequest";


interface ISongFields {
    title: string,
    duration: string,
    singerId: number,

    genreIds: number[]
    file: any,
    album: string,
    createdYear: number,
    picture: null
}
export const AddSongRequest = (song: ISongFields) => {
    return fileRequest('POST', `/songs`, {data: song})
}