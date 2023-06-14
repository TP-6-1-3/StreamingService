import { useStore } from 'effector-react';
import React, {useState} from 'react';
import { MusicElementMoreIcon } from '../../entities/icons/musicElementMore';
import { MusicFooterPlayerPauseIcon } from '../../entities/icons/musicFooterPlayerPause';
import { MusicFooterPlayerPlayIcon } from '../../entities/icons/musicFooterPlayerPlay';
import { ISong } from '../../shared/api/songs/getSongs';
import { $currentTrack, $currentTrackIsPaused, setCurrentTrackFx, setCurrentTrackIsPausedFx, setCurrentTrackSongIdFx } from '../../shared/stores/tracks';
import { MusicElementActions, MusicElementContainer, MusicElementContent, MusicElementImageContainer, MusicElementName } from './styled';
import {MusicElementOptions} from "../../entities/musicElementOptions";
import {Box, Fade, Popper} from "@mui/material";
import {LibraryMusicElementOptions} from "../libraryMusicElementOptions";

export const MusicElement = (params: ISong) => {
    const currentTrack = useStore($currentTrack);
    const currentTrackIsPaused = useStore($currentTrackIsPaused);

    const {
        songId,
        title,
        duration,
        singer,
        genres
    } = params;

    const {
        singerId,
        fullName,
        description
    } = singer;

    const isSongPlayed = currentTrack ? songId === currentTrack.songId : false;
    const songIcon = isSongPlayed && !currentTrackIsPaused ? <MusicFooterPlayerPauseIcon /> : <MusicFooterPlayerPlayIcon />;

    const [optionsIsOpen, setOptionsIsOpen] = useState(false);
    const onHandleOpenOptions = (e: any) => {
        optionsIsOpen ? setOptionsIsOpen(false) : setOptionsIsOpen(true);
    }


    return (
        <MusicElementContainer active={isSongPlayed}>
            <MusicElementContent>
                <div onClick={() => {
                    setCurrentTrackIsPausedFx(!currentTrackIsPaused);
                    setCurrentTrackFx(params);
                    setCurrentTrackSongIdFx(songId);
                }}>{ songIcon }</div>

                <MusicElementImageContainer>
                    <img src="/assets/music.png" alt="" />
                </MusicElementImageContainer>

                <MusicElementName>({duration}) {fullName} - {title}</MusicElementName>

                <MusicElementActions>
                    <div onClick={(e) => onHandleOpenOptions(e)}>
                        <MusicElementMoreIcon />
                    </div>

                    {optionsIsOpen && (<LibraryMusicElementOptions songId={songId} />)}

                </MusicElementActions>
            </MusicElementContent>
        </MusicElementContainer>
    )
}