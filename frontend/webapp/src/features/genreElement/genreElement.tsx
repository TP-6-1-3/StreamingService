import React from 'react'
import {GenreElementContainer, GenreElementContent, GenreElementName} from "./styled";
import {useNavigate} from "react-router-dom";
import {IGenre} from "../../shared/api/genres/getGenres";
import {GetSongsRequest} from "../../shared/api/songs/getSongs";
import {$tracksList, setTracksListFx} from "../../shared/stores/tracks";
import {useStore} from "effector-react";


export const GenreElement = (props: IGenre): React.ReactElement<void, string> => {

    const musicList = useStore($tracksList);
    const onHandleLoadTracksByGenre = (genreId: any) => {
        console.log(genreId)
        GetSongsRequest({ genres: [genreId], count: 20,
            isAsc: false })
            .then(trackData => {
                const trackList = trackData.data;
                setTracksListFx(trackList);
            })
    }


    return (
        <div onClick={() => onHandleLoadTracksByGenre(props.genreId)}>
            <GenreElementContainer>
                <GenreElementContent>

                    <GenreElementName>{props.name}</GenreElementName>

                </GenreElementContent>
            </GenreElementContainer>
        </div>

    )
}