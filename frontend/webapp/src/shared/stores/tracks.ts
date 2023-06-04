import { createEvent, createStore } from "effector";
import { ISong } from "../api/songs/getSongs";

export const $tracksList = createStore<ISong[] | []>([]);
export const setTracksListFx = createEvent<ISong[] | []>();
$tracksList.on(setTracksListFx, (_, tracks: ISong[] | []) => tracks);

export const $currentTrack = createStore<ISong | null>(null);
export const setCurrentTrackFx = createEvent<ISong>();
$currentTrack.on(setCurrentTrackFx, (_, tracks: ISong) => tracks);

export const $currentTrackIsPaused = createStore<boolean>(true);
export const setCurrentTrackIsPausedFx = createEvent<boolean>();
$currentTrackIsPaused.on(setCurrentTrackIsPausedFx, (_, state: boolean) => state);