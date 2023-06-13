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
import { AvatarContainer, AvatarImg, HomeActionButton, HomeActionContainer, HomeComponent, HomeHeaderFirstText, HomeHeaderSecondText, HomeHelloContent, HomeLayout, HomeMusicContent, MusicPublishContainer, MusicPublishImageContainer, MusicPublishPlayerActions, MusicPublishPlayerSlider, ProfileContentContainer, ProfileDataContainer, ProfileDataGrid, ProfileDataGridElement, ProfileDataTitle, ProfileDataValue, ProfileHeaderText, ProfileName } from "./styled"

export const ProfilePage = (): React.ReactElement<void, string> => {
    const userData = useStore($userData);

    const userRowsRender = userData ? Object.keys(userData).map(item => {
        const keyDictionary: any = {
            "userId": "Код",
            "firstName": "Имя",
            "lastName": "Фамилия",
            "email": "Электронная почта",
            "nickname": "Никнейм",
            "isVerified": "Аккаунт верифицирован",
        }

        const textDictionary = keyDictionary[item];
        let renderText = "";
        switch (item) {
            case "isVerified":
                renderText = (userData as any)[item] ? "Да" : "Нет";
                break;

            default:
                renderText = (userData as any)[item];
                break;
        }

        return textDictionary ? (
            <ProfileDataGridElement>
                <ProfileDataTitle>{textDictionary}</ProfileDataTitle>
                <ProfileDataValue>{renderText}</ProfileDataValue>
            </ProfileDataGridElement>
        ) : null;
    }).filter(item => item) : null;

    React.useEffect(() => {
        setHeaderTextFx('Профиль');
    }, []);

    console.log(userData);

    return (
        <HomeComponent>
            <HeaderWrapper>
                <PersonalHeader />
            </HeaderWrapper>

            <HomeLayout>
                <ProfileDataContainer>
                    <ProfileHeaderText>Ваши данные</ProfileHeaderText>

                    <ProfileContentContainer>
                        <AvatarContainer>
                            <AvatarImg src="/assets/emptyAvatar.png" alt="" />
                        </AvatarContainer>

                        <ProfileName>Test</ProfileName>

                        <ProfileDataGrid>
                            { userRowsRender }
                        </ProfileDataGrid>
                    </ProfileContentContainer>
                </ProfileDataContainer>

                <MusicPlayerFooter />
            </HomeLayout>
        </HomeComponent>
    )
}