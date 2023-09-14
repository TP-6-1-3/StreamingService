import React from 'react';
import {MusicElementOptions} from "../../entities/musicElementOptions";
import {MusicElementOptionsItem, MusicElementOptionsItemList} from '../../entities/musicElementOptions/styled';
import {DeleteFromLibrary} from "../../shared/api/library/deleteFromLibrary";
import {useStore} from "effector-react";
import {$modalIsOpen, $setModalOpenFx} from "../../shared/stores/modal";
import {$recomend, $setRecomendFx} from "../../shared/stores/recomend";

interface ILibraryMusicElementOptions{
    songId: any,
    openModal: Function
}
export const LibraryMusicElementOptions = (props: ILibraryMusicElementOptions): React.ReactElement<ILibraryMusicElementOptions, string> => {

    const onHandleDeleteTrack = () => {
        DeleteFromLibrary(props.songId).then(res => console.log(res))
    }

    const modalIsOpen = $modalIsOpen.getState();
    // console.log($modalIsOpen.getState())


    $setRecomendFx(props.songId);


    const isOpenModal = useStore($modalIsOpen);

    const openModal = () => {
        $setModalOpenFx(true)
    }


    return (
        <MusicElementOptions>
            <MusicElementOptionsItemList>
                <MusicElementOptionsItem onClick={onHandleDeleteTrack}>Удалить</MusicElementOptionsItem>
                <MusicElementOptionsItem onClick={openModal}>Рекомендовать</MusicElementOptionsItem>
            </MusicElementOptionsItemList>
        </MusicElementOptions>
    )
}