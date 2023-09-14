import { Button, TextField } from '@mui/material';
import { useStore } from 'effector-react';
import React from 'react';
import { Link } from "react-router-dom"
import { HeaderWrapper } from "../../entities/headerWrapper"
import { MusicPlayerLeftIcon } from "../../entities/icons/musicPlayerLeft"
import { MusicPlayerRightIcon } from "../../entities/icons/musicPlayerRight"
import { MusicSliderElemIcon } from "../../entities/icons/musicSliderElem"
import { FriendElement } from '../../features/friendElement';
import { MusicPlayerFooter } from "../../features/musicFooterPlayer"
import { PersonalHeader } from "../../features/personalHeader"
import { SearchInput } from "../../features/searchInput"
import { AddFriendRequest } from '../../shared/api/friends/addFriend';
import { GetFriendsRequest } from '../../shared/api/friends/getFriends';
import { setHeaderTextFx } from '../../shared/stores/common';
import { $friendsList, setFriendsListFx } from '../../shared/stores/friends';
import { $userData } from '../../shared/stores/user';
import { HeaderText } from "../../shared/text/headerText"
import { AvatarContainer, AvatarImg, FriendAddContent, FriendAddInput, FriendAddText, FriendErrorText, FriendHeaderText, FriendsAddContainer, FriendsContentContainer, FriendsList, FriendsListContainer, HomeActionButton, HomeActionContainer, HomeComponent, HomeHeaderFirstText, HomeHeaderSecondText, HomeHelloContent, HomeLayout, HomeMusicContent, MusicPublishContainer, MusicPublishImageContainer, MusicPublishPlayerActions, MusicPublishPlayerSlider, ProfileContentContainer, ProfileDataContainer } from "./styled"

export const FriendsPage = (): React.ReactElement<void, string> => {
    const userData = useStore($userData);
    const friendsList = useStore($friendsList);
    const [friendsGetted, setFriendsGetted] = React.useState(false);
    const [friendName, setFriendName] = React.useState<any>(null);
    const [error, setError] = React.useState<any>(null);

    React.useEffect(() => {
        setHeaderTextFx('Друзья');
    }, []);

    React.useEffect(() => {
        if (!friendsGetted && userData) {
            const nickname = userData.nickname;
            GetFriendsRequest(nickname).then(data => {
                setFriendsListFx(data);
                setFriendsGetted(true);
            })
        }
    }, [userData, friendsGetted])

    const addFriend = () => {
        if (!userData) return;
        const nickname = userData.nickname;

        AddFriendRequest(friendName)
        .then(data => {
            setFriendName("");

            if (data.message) {
                setError(data.message);
                return;
            }

            GetFriendsRequest(nickname).then(data => {
                setFriendsListFx(data);
            })
        })
        .catch(err => {
            console.log(err)
        })
    }

    const friendsRender = friendsList?.data.map(item => {
        return <FriendElement {...item} />
    })

    console.log(userData);

    return (
        <HomeComponent>
            <HeaderWrapper>
                <PersonalHeader />
            </HeaderWrapper>

            <HomeLayout>
                <FriendsContentContainer>
                    <FriendsListContainer>
                        <FriendHeaderText>Ваши друзья</FriendHeaderText>

                        <FriendsList>
                            { friendsRender }
                        </FriendsList>
                    </FriendsListContainer>
                    <FriendsAddContainer>
                        <FriendHeaderText>Добавить друга</FriendHeaderText>

                        <FriendAddText>Введите логин пользователя, чтобы добавить его в друзья.</FriendAddText>

                        <FriendAddContent>
                            <FriendAddInput>
                                <TextField
                                    required
                                    id="outlined-required"
                                    label="Логин"
                                    defaultValue=""
                                    value={friendName}
                                    autoComplete='off'
                                    onChange={e => {
                                        setError(null);
                                        setFriendName(e.currentTarget.value);
                                    }}
                                />
                            </FriendAddInput>

                            <Button variant="contained" size="large" onClick={addFriend}>Добавить</Button>

                            <FriendErrorText>
                                { error }
                            </FriendErrorText>
                        </FriendAddContent>
                    </FriendsAddContainer>
                </FriendsContentContainer>

                <MusicPlayerFooter />
            </HomeLayout>
        </HomeComponent>
    )
}