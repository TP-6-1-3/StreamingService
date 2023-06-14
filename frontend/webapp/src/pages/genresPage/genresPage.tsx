import React from 'react';
import {HeaderWrapper} from "../../entities/headerWrapper";
import {PersonalHeader} from "../../features/personalHeader";
import {HomeComponent, HomeGenresContent, HomeGenresTracks, HomeLayout} from "./styled";
import {setHeaderTextFx} from "../../shared/stores/common";
import {GetGenresRequest} from "../../shared/api/genres/getGenres";
import {$genresList, setGenresListFx} from "../../shared/stores/genres";
import {useStore} from "effector-react";
import {GenreElement} from "../../features/genreElement";
import {MusicElement} from "../../features/musicElement";
import {$tracksList, setTracksListFx} from "../../shared/stores/tracks";
import {GetSongsRequest} from "../../shared/api/songs/getSongs";
import {MusicPlayerFooter} from "../../features/musicFooterPlayer";


export const GenresPage = (): React.ReactElement<void, string> => {

    const genresList = useStore($genresList);
    const musicList = useStore($tracksList);

    React.useEffect(() => {
        setHeaderTextFx("Жанры");

        GetGenresRequest()
            .then(genresData => {
                if(genresData) {
                    const genresList = genresData
                    setGenresListFx(genresList);
                }
            })
    }, [])



    const renderGenresList = genresList.map(genreParams => (
        <GenreElement
            key={genreParams.genreId}
            {...genreParams}
        />
    ));
    const renderMusicList = musicList.map(musicParams => <MusicElement key={musicParams.songId} {...musicParams} />)

    return (
        <HomeComponent>
            <HeaderWrapper>
                <PersonalHeader />
            </HeaderWrapper>

            <HomeLayout>
                <HomeGenresContent>
                    {renderGenresList}
                </HomeGenresContent>

                <HomeGenresTracks>
                    {renderMusicList}
                </HomeGenresTracks>
                <MusicPlayerFooter />
            </HomeLayout>
        </HomeComponent>
    )
}