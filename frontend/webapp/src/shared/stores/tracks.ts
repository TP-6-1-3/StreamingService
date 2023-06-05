import { createEvent, createStore } from "effector";
import { ISong } from "../api/songs/getSongs";
import { projectConfig } from "../config/project";

export const $tracksList = createStore<ISong[] | []>([]);
export const setTracksListFx = createEvent<ISong[] | []>();
$tracksList.on(setTracksListFx, (_, tracks: ISong[] | []) => {
    const getUrl = (songId: number) => `${projectConfig.API_URL}/songs/${songId}/file`;
    return tracks.map(track => Object.assign(track, { url: getUrl(track.songId) }));
});

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
            setCurrentTrackSongIdFx(nextTrack.songId);

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
            setCurrentTrackSongIdFx(prevTrack.songId);

            return prevTrack;
        }
        return null;
    })
    .on(setCurrentTrackFx, (_, track: ISong) => track);

export const $currentTrackIsPaused = createStore<boolean>(true);
export const setCurrentTrackIsPausedFx = createEvent<boolean>();
$currentTrackIsPaused.on(setCurrentTrackIsPausedFx, (_, state: boolean) => state);

export const $currentTrackSongId = createStore<number | null>(null);
export const setCurrentTrackSongIdFx = createEvent<number>();
$currentTrackSongId.on(setCurrentTrackSongIdFx, (_, state: number) => state);

export const $currentTrackAudioObj = createStore<any>(null);
export const setCurrentTrackAudioObjFx = createEvent<any>();
$currentTrackAudioObj.on(setCurrentTrackAudioObjFx, (_, state: any) => state);