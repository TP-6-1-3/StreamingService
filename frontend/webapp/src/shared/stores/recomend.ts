import { createEvent, createStore } from "effector";
import { projectConfig } from "../config/project";


export interface IRecomend {
    "songId": number,
}
export const $recomend = createStore<number>(0);
export const $setRecomendFx = createEvent<number>();

$recomend.on($setRecomendFx, (_, state: any) => state);