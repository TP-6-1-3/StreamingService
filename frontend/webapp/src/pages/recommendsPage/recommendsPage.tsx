import React from 'react';
import {HomeLayout, RecomendsComponent, RecommendsContent, RecommendsHeaderContent} from "./styled";
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

export const RecommendsPage = (): React.ReactElement<void, string> => {
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
            </HomeLayout>
        </RecomendsComponent>
    )
}