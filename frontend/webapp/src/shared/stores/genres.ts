import { createEvent, createStore } from "effector";
import { IGenre } from "../api/genres/getGenres";
import { projectConfig } from "../config/project";

export const $genresList = createStore<IGenre[] | []>([]);
export const setGenresListFx = createEvent<IGenre[] | []>();
$genresList.on(setGenresListFx, (_, genres: IGenre[] | []) => {
    const getUrl = (genreId: number) => `${projectConfig.API_URL}/genres/${genreId}`;
    return genres.map(genre => Object.assign(genre, { url: getUrl(genre.genreId) }));
});
