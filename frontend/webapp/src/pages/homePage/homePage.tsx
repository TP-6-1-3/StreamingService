import { Link } from "react-router-dom"
import { HeaderWrapper } from "../../entities/headerWrapper"
import { MusicPlayerLeftIcon } from "../../entities/icons/musicPlayerLeft"
import { MusicPlayerRightIcon } from "../../entities/icons/musicPlayerRight"
import { MusicSliderElemIcon } from "../../entities/icons/musicSliderElem"
import { MusicPlayerFooter } from "../../features/musicFooterPlayer"
import { PersonalHeader } from "../../features/personalHeader"
import { SearchInput } from "../../features/searchInput"
import { HeaderText } from "../../shared/text/headerText"
import { HomeActionButton, HomeActionContainer, HomeComponent, HomeHeaderFirstText, HomeHeaderSecondText, HomeHelloContent, HomeLayout, HomeMusicContent, MusicPublishContainer, MusicPublishImageContainer, MusicPublishPlayerActions, MusicPublishPlayerSlider } from "./styled"

export const HomePage = (): React.ReactElement<void, string> => {
    return (
        <HomeComponent>
            <HeaderWrapper>
                <PersonalHeader/>
            </HeaderWrapper>

            <HomeLayout>
                <HomeHelloContent>
                    <HomeHeaderFirstText>Добро пожаловать, Глеб!</HomeHeaderFirstText>

                    <HomeHeaderSecondText>Хотите ознакомиться с вашими персональными треками за сегодня?</HomeHeaderSecondText>

                    <HomeActionContainer>
                        <HomeHeaderSecondText>Или же вы также можете:</HomeHeaderSecondText>
                        <Link to="#"><HomeActionButton>Перейти к своим любимым песням</HomeActionButton></Link>
                        <Link to="#"><HomeActionButton>Посмотреть композиции по жанрам</HomeActionButton></Link>
                        <Link to="#"><HomeActionButton>Открыть историю прослушиваний</HomeActionButton></Link>
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
        </HomeComponent>
    )
}