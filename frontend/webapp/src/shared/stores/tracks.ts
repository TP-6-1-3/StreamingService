import { createEvent, createStore } from "effector";
import { ISong } from "../api/songs/getSongs";

export const $tracksList = createStore<ISong[] | []>([]);
export const setTracksListFx = createEvent<ISong[] | []>();
$tracksList.on(setTracksListFx, (_, tracks: ISong[] | []) => tracks);

export const $currentTrack = createStore<ISong | null>(null);
export const setCurrentTrackFx = createEvent<ISong>();
export const setNextTrackFx = createEvent();
export const setPrevTrackFx = createEvent();
$currentTrack
    .on(setNextTrackFx, (song: ISong | null) => {
        if (song) {
            const currentSongId = song.songId;
            const tracks = $tracksList.getState();

            const tracksIds = tracks.map(item => item.songId);
            const currentTrackKeyof = tracksIds.indexOf(currentSongId);
            const newKeyTrack = currentTrackKeyof + 1 < tracks.length ? currentTrackKeyof + 1 : 0
            
            const nextTrack = tracks[newKeyTrack];

            return nextTrack;
        }
        return null;
    })
    .on(setPrevTrackFx, (song: ISong | null) => {
        if (song) {
            const currentSongId = song.songId;
            const tracks = $tracksList.getState();

            const tracksIds = tracks.map(item => item.songId);
            const currentTrackKeyof = tracksIds.indexOf(currentSongId);
            const newKeyTrack = currentTrackKeyof - 1 >= 0 ? currentTrackKeyof - 1 : tracks.length - 1;
            
            const prevTrack = tracks[newKeyTrack];

            return prevTrack;
        }
        return null;
    })
    .on(setCurrentTrackFx, (_, tracks: ISong) => tracks);

export const $currentTrackIsPaused = createStore<boolean>(true);
export const setCurrentTrackIsPausedFx = createEvent<boolean>();
$currentTrackIsPaused.on(setCurrentTrackIsPausedFx, (_, state: boolean) => state);