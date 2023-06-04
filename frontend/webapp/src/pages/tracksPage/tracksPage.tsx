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
import { setHeaderTextFx } from '../../shared/stores/common';
import { HeaderText } from "../../shared/text/headerText"
import { HomeActionButton, HomeActionContainer, HomeComponent, HomeHeaderFirstText, HomeHeaderSecondText, HomeHelloContent, HomeLayout, HomeMusicContent, MusicDescription, MusicDescriptionLabel, MusicDescriptionValue, MusicDesctiptionElement, MusicListContainer, MusicPublishContainer, MusicPublishImageContainer, MusicPublishPlayContainer, MusicPublishPlayElement, MusicPublishPlayerActions, MusicPublishPlayerSlider } from "./styled"

export const TracksPage = (): React.ReactElement<void, string> => {
    React.useEffect(() => {
        setHeaderTextFx('Мои треки');
    }, []);

    return (
        <HomeComponent>
            <HeaderWrapper>
                <PersonalHeader />
            </HeaderWrapper>

            <HomeLayout>
                <HomeHelloContent>
                    <MusicListContainer>
                        <MusicElement />
                        <MusicElement />
                        <MusicElement />
                        <MusicElement />
                        <MusicElement />
                        <MusicElement />
                        <MusicElement />
                        <MusicElement />
                        <MusicElement />
                        <MusicElement />
                        <MusicElement />
                        <MusicElement />
                        <MusicElement />
                        <MusicElement />
                        <MusicElement />
                        <MusicElement />
                        <MusicElement />
                        <MusicElement />
                        <MusicElement />
                        <MusicElement />
                        <MusicElement />
                        <MusicElement />
                        <MusicElement />
                        <MusicElement />
                        <MusicElement />
                        <MusicElement />
                        <MusicElement />
                        <MusicElement />
                        <MusicElement />
                    </MusicListContainer>
                </HomeHelloContent>
                <HomeMusicContent>
                    <Link to="#"><HomeActionButton>История прослушиваний</HomeActionButton></Link>

                    <MusicPublishContainer>
                        <MusicPublishPlayContainer>
                            <MusicPublishImageContainer>
                                <img src="https://crosti.ru/patterns/00/17/01/73_picture_e6ef3066.jpg" alt="" />
                            </MusicPublishImageContainer>
                            <MusicPublishPlayElement>
                                <MusicFooterPlayerPlayIcon />
                                <span>Слушать</span>
                            </MusicPublishPlayElement>
                        </MusicPublishPlayContainer>

                        <MusicDescription>
                            <MusicDesctiptionElement>
                                <MusicDescriptionLabel>Название:</MusicDescriptionLabel>
                                <MusicDescriptionValue>123</MusicDescriptionValue>
                            </MusicDesctiptionElement>
                            <MusicDesctiptionElement>
                                <MusicDescriptionLabel>Композиция:</MusicDescriptionLabel>
                                <MusicDescriptionValue>123</MusicDescriptionValue>
                            </MusicDesctiptionElement>
                            <MusicDesctiptionElement>
                                <MusicDescriptionLabel>Альбом:</MusicDescriptionLabel>
                                <MusicDescriptionValue>123</MusicDescriptionValue>
                            </MusicDesctiptionElement>
                            <MusicDesctiptionElement>
                                <MusicDescriptionLabel>Год выпуска:</MusicDescriptionLabel>
                                <MusicDescriptionValue>123</MusicDescriptionValue>
                            </MusicDesctiptionElement>
                            <MusicDesctiptionElement>
                                <MusicDescriptionLabel>Жанр:</MusicDescriptionLabel>
                                <MusicDescriptionValue>123</MusicDescriptionValue>
                            </MusicDesctiptionElement>
                        </MusicDescription>
                    </MusicPublishContainer>
                </HomeMusicContent>

                <MusicPlayerFooter />
            </HomeLayout>
        </HomeComponent>
    )
}