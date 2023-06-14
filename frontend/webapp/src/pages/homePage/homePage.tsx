import { useStore } from 'effector-react';
import React from 'react';
import { Link } from "react-router-dom"
import { HeaderWrapper } from "../../entities/headerWrapper"
import { MusicPlayerLeftIcon } from "../../entities/icons/musicPlayerLeft"
import { MusicPlayerRightIcon } from "../../entities/icons/musicPlayerRight"
import { MusicSliderElemIcon } from "../../entities/icons/musicSliderElem"
import { MusicPlayerFooter } from "../../features/musicFooterPlayer"
import { PersonalHeader } from "../../features/personalHeader"
import { SearchInput } from "../../features/searchInput"
import { setHeaderTextFx } from '../../shared/stores/common';
import { $userData } from '../../shared/stores/user';
import { HeaderText } from "../../shared/text/headerText"
import { HomeActionButton, HomeActionContainer, HomeComponent, HomeHeaderFirstText, HomeHeaderSecondText, HomeHelloContent, HomeLayout, HomeMusicContent, MusicPublishContainer, MusicPublishImageContainer, MusicPublishPlayerActions, MusicPublishPlayerSlider } from "./styled"

export const HomePage = (): React.ReactElement<void, string> => {
    const userData = useStore($userData);

    const firstName = userData?.firstName;
    const authedText = firstName ? `Добро пожаловать, ${firstName}!` : `Добро пожаловать!`

    React.useEffect(() => {
        setHeaderTextFx('Главная');
    }, []);

    return (
        <HomeComponent>
            <HeaderWrapper>
                <PersonalHeader/>
            </HeaderWrapper>



            <HomeLayout>
                <HomeHelloContent>
                    <HomeHeaderFirstText>{authedText}</HomeHeaderFirstText>

                    <HomeHeaderSecondText>Хотите ознакомиться с вашими персональными треками за сегодня?</HomeHeaderSecondText>

                    <HomeActionContainer>
                        <HomeHeaderSecondText>Или же вы также можете:</HomeHeaderSecondText>
                        <Link to={userData ? "/tracks" : "/auth"}><HomeActionButton>Перейти к своим любимым песням</HomeActionButton></Link>
                        <Link to={userData ? "/genres" : "/auth"}><HomeActionButton>Посмотреть композиции по жанрам</HomeActionButton></Link>
                        <Link to={userData ? "#" : "/auth"}><HomeActionButton>Открыть историю прослушиваний</HomeActionButton></Link>
                    </HomeActionContainer>
                </HomeHelloContent>
                <HomeMusicContent>
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
                </HomeMusicContent>

                <MusicPlayerFooter />
            </HomeLayout>

            <HomeLayout></HomeLayout>
        </HomeComponent>
    )
}