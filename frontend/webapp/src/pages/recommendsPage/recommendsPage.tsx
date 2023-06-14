import React from 'react';
import {RecomendsComponent, RecommendsContent, RecommendsHeaderContent} from "./styled";
import {PersonalHeader} from "../../features/personalHeader";
import {HeaderWrapper} from "../../entities/headerWrapper";
import {HomeHeaderFirstText, HomeHeaderSecondText} from "./styled";
import {
    MusicPublishContainer,
    MusicPublishImageContainer,
    MusicPublishPlayerActions,
    MusicPublishPlayerSlider
} from "./styled";
import {MusicPlayerLeftIcon} from "../../entities/icons/musicPlayerLeft";
import {MusicSliderElemIcon} from "../../entities/icons/musicSliderElem";
import {MusicPlayerRightIcon} from "../../entities/icons/musicPlayerRight";
import {useStore} from "effector-react";
import {$currentTrack, $tracksList, setTracksListFx} from "../../shared/stores/tracks";
import {setHeaderTextFx} from "../../shared/stores/common";
import {GetSongsFromLibraryRequest} from "../../shared/api/library/getFromLibrary";
import {GetRecomend} from "../../shared/api/recomend/getRecomend";
import {MusicElement} from "../../features/musicElement";
import {HomeGenresTracks, HomeLayout} from "../genresPage/styled";
import {MusicPlayerFooter} from "../../features/musicFooterPlayer";

export const RecommendsPage = (): React.ReactElement<void, string> => {

    const musicList = useStore($tracksList);
    const currentTrack = useStore($currentTrack);

    React.useEffect(() => {
        setHeaderTextFx('Моя подборка');

        GetRecomend().then(musicData => {
            if (musicData) {
                const musicList = musicData.data;
                console.log(musicList)
                setTracksListFx(musicData);
            }
        })
    }, []);


    const renderMusicList = musicList.map(musicParams => <MusicElement key={musicParams.songId} {...musicParams} />)

    return (
        <RecomendsComponent>
            <HeaderWrapper>
                <PersonalHeader/>
            </HeaderWrapper>

            <HomeLayout>


                <RecommendsHeaderContent>
                    <HomeHeaderFirstText>Это ваша подборка.</HomeHeaderFirstText>
                    <HomeHeaderSecondText>Посмотрите, что вам порекомендовала система и друзья:</HomeHeaderSecondText>
                </RecommendsHeaderContent>
                <HomeGenresTracks>
                    {renderMusicList}
                </HomeGenresTracks>


                <RecommendsContent>


                    <MusicPublishContainer>
                        <p>Исполнитель - Композиция</p>

                        <MusicPublishImageContainer>
                            <img src="https://crosti.ru/patterns/00/17/01/73_picture_e6ef3066.jpg" alt="" />
                        </MusicPublishImageContainer>

                        <MusicPublishPlayerActions>
                            <MusicPlayerLeftIcon />

                            <MusicPublishPlayerSlider>
                                <MusicSliderElemIcon />
                                <MusicSliderElemIcon />
                                <MusicSliderElemIcon />
                                <MusicSliderElemIcon />
                                <MusicSliderElemIcon />
                            </MusicPublishPlayerSlider>

                            <MusicPlayerRightIcon />
                        </MusicPublishPlayerActions>
                    </MusicPublishContainer>
                </RecommendsContent>
                <MusicPlayerFooter />
            </HomeLayout>
        </RecomendsComponent>
    )
}