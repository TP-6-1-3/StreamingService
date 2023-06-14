import React from 'react';
import {MusicElementOptions} from "../../entities/musicElementOptions";
import {MusicElementOptionsItem, MusicElementOptionsItemList} from '../../entities/musicElementOptions/styled';
import {DeleteFromLibrary} from "../../shared/api/library/deleteFromLibrary";

export const LibraryMusicElementOptions = (songId: any) => {

    const onHandleDeleteTrack = () => {
        DeleteFromLibrary(songId.songId).then(res => console.log(res))
    }

    return (
        <MusicElementOptions>
            <MusicElementOptionsItemList>
                <MusicElementOptionsItem onClick={onHandleDeleteTrack}>Удалить</MusicElementOptionsItem>
                <MusicElementOptionsItem>Рекомендовать</MusicElementOptionsItem>
            </MusicElementOptionsItemList>
        </MusicElementOptions>
    )
}