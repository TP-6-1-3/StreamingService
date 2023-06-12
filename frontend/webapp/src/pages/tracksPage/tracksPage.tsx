import { useStore } from 'effector-react';
import React from 'react';
import { Link } from "react-router-dom"
import { HeaderWrapper } from "../../entities/headerWrapper"
import { MusicFooterPlayerPlayIcon } from '../../entities/icons/musicFooterPlayerPlay';
import { MusicPlayerLeftIcon } from "../../entities/icons/musicPlayerLeft"
import { MusicPlayerRightIcon } from "../../entities/icons/musicPlayerRight"
import { MusicSliderElemIcon } from "../../entities/icons/musicSliderElem"
import { MusicElement } from '../../features/musicElement';
import { MusicPlayerFooter } from "../../features/musicFooterPlayer"
import { PersonalHeader } from "../../features/personalHeader"
import { SearchInput } from "../../features/searchInput"
import { GetSongsRequest } from '../../shared/api/songs/getSongs';
import { setHeaderTextFx } from '../../shared/stores/common';
import { $currentTrack, $tracksList, setTracksListFx } from '../../shared/stores/tracks';
import { HeaderText } from "../../shared/text/headerText"
import { HomeActionButton, HomeActionContainer, HomeComponent, HomeHeaderFirstText, HomeHeaderSecondText, HomeHelloContent, HomeLayout, HomeMusicContent, MusicDescription, MusicDescriptionLabel, MusicDescriptionValue, MusicDesctiptionElement, MusicListContainer, MusicPublishContainer, MusicPublishImageContainer, MusicPublishPlayContainer, MusicPublishPlayElement, MusicPublishPlayerActions, MusicPublishPlayerSlider } from "./styled"

export const TracksPage = (): React.ReactElement<void, string> => {
    const musicList = useStore($tracksList);
    const currentTrack = useStore($currentTrack);

    React.useEffect(() => {
        setHeaderTextFx('Мои треки');

        GetSongsRequest({
            count: 20,
            isAsc: false
        }).then(musicData => {
            if (musicData) {
                const musicList = musicData.data;
                setTracksListFx(musicList);
            }
        })
    }, []);

    const renderMusicList = musicList.map(musicParams => {
        return <MusicElement {...musicParams} />
    })

    const renderDescription = currentTrack ? (
        <>
            <MusicDesctiptionElement>
                <MusicDescriptionLabel>Автор:</MusicDescriptionLabel>
                <MusicDescriptionValue>{currentTrack.singer.fullName}</MusicDescriptionValue>
            </MusicDesctiptionElement>
            <MusicDesctiptionElement>
                <MusicDescriptionLabel>Композиция:</MusicDescriptionLabel>
                <MusicDescriptionValue>{currentTrack.title}</MusicDescriptionValue>
            </MusicDesctiptionElement>
            <MusicDesctiptionElement>
                <MusicDescriptionLabel>Продолжительность:</MusicDescriptionLabel>
                <MusicDescriptionValue>{currentTrack.duration}</MusicDescriptionValue>
            </MusicDesctiptionElement>
            <MusicDesctiptionElement>
                <MusicDescriptionLabel>Жанр:</MusicDescriptionLabel>
                <MusicDescriptionValue>{currentTrack.genres.map(item => item.name).join(', ')}</MusicDescriptionValue>
            </MusicDesctiptionElement>
        </>
    ) : null;

    return (
        <HomeComponent>
            <HeaderWrapper>
                <PersonalHeader />
            </HeaderWrapper>

            <HomeLayout>
                <HomeHelloContent>
                    <MusicListContainer>
                        {renderMusicList}
                    </MusicListContainer>
                </HomeHelloContent>
                <HomeMusicContent>
                    <Link to="#"><HomeActionButton>История прослушиваний</HomeActionButton></Link>

                    <MusicPublishContainer>
                        <MusicPublishPlayContainer>
                            <MusicPublishImageContainer>
                                <img src="/assets/music.png" alt="" />
                            </MusicPublishImageContainer>
                            <MusicPublishPlayElement>
                                <MusicFooterPlayerPlayIcon />
                                <span>Слушать</span>
                            </MusicPublishPlayElement>
                        </MusicPublishPlayContainer>

                        <MusicDescription>
                            { renderDescription }
                        </MusicDescription>
                    </MusicPublishContainer>
                </HomeMusicContent>

                <MusicPlayerFooter />
            </HomeLayout>
        </HomeComponent>
    )
}