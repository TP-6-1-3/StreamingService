import { useStore } from 'effector-react';
import React from 'react';
import { MusicElementMoreIcon } from '../../entities/icons/musicElementMore';
import { MusicFooterPlayerPauseIcon } from '../../entities/icons/musicFooterPlayerPause';
import { MusicFooterPlayerPlayIcon } from '../../entities/icons/musicFooterPlayerPlay';
import { ISong } from '../../shared/api/songs/getSongs';
import { $currentTrack, $currentTrackIsPaused, setCurrentTrackFx, setCurrentTrackIsPausedFx } from '../../shared/stores/tracks';
import { MusicElementActions, MusicElementContainer, MusicElementContent, MusicElementImageContainer, MusicElementName } from './styled';

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

    return (
        <MusicElementContainer active={isSongPlayed}>
            <MusicElementContent>
                <div onClick={() => {
                    setCurrentTrackIsPausedFx(!currentTrackIsPaused);
                    setCurrentTrackFx(params);
                }}>{ songIcon }</div>

                <MusicElementImageContainer>
                    <img src="/assets/music.png" alt="" />
                </MusicElementImageContainer>

                <MusicElementName>({duration}) {fullName} - {title}</MusicElementName>

                <MusicElementActions>
                    <MusicElementMoreIcon />
                </MusicElementActions>
            </MusicElementContent>
        </MusicElementContainer>
    )
}