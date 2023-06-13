import { useStore } from 'effector-react';
import React from 'react';
import { MusicElementMoreIcon } from '../../entities/icons/musicElementMore';
import { MusicFooterPlayerPauseIcon } from '../../entities/icons/musicFooterPlayerPause';
import { MusicFooterPlayerPlayIcon } from '../../entities/icons/musicFooterPlayerPlay';
import { DeleteFriendRequest } from '../../shared/api/friends/deleteFriend';
import { GetFriendsRequest, IFriendsListRequest } from '../../shared/api/friends/getFriends';
import { ISong } from '../../shared/api/songs/getSongs';
import { setFriendsListFx } from '../../shared/stores/friends';
import { $currentTrack, $currentTrackIsPaused, setCurrentTrackFx, setCurrentTrackIsPausedFx, setCurrentTrackSongIdFx } from '../../shared/stores/tracks';
import { AvatarContainer, AvatarImg, DeleteFriendContainer, FriendElementContainer, FriendName } from './styled';

const DeleteFriend = ({ nickname }: { nickname: string }) => {
    const deleteFriend = () => {
        DeleteFriendRequest(nickname).then(data => {
            GetFriendsRequest().then(data => {
                setFriendsListFx(data);
            })
        });
    }

    return (
        <DeleteFriendContainer onClick={deleteFriend}>
            <svg width="28" height="28" viewBox="0 0 28 28" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M20.6875 18.8125L15.875 14L20.6875 9.1875L18.8125 7.3125L14 12.125L9.1875 7.3125L7.3125 9.1875L12.125 14L7.3125 18.8125L9.1875 20.6875L14 15.875L18.8125 20.6875L20.6875 18.8125ZM4.5625 4.625C7.1875 2 10.3333 0.6875 14 0.6875C17.6667 0.6875 20.7917 2 23.375 4.625C26 7.20833 27.3125 10.3333 27.3125 14C27.3125 17.6667 26 20.8125 23.375 23.4375C20.7917 26.0208 17.6667 27.3125 14 27.3125C10.3333 27.3125 7.1875 26.0208 4.5625 23.4375C1.97917 20.8125 0.6875 17.6667 0.6875 14C0.6875 10.3333 1.97917 7.20833 4.5625 4.625Z" fill="white" />
            </svg>
        </DeleteFriendContainer>
    )
}

export const FriendElement = ({ userId, nickname }: IFriendsListRequest["data"][0]) => {
    const currentTrack = useStore($currentTrack);
    const currentTrackIsPaused = useStore($currentTrackIsPaused);

    return (
        <FriendElementContainer>
            <AvatarContainer>
                <AvatarImg src="/assets/emptyAvatar.png" alt="" />
            </AvatarContainer>

            <FriendName>{nickname}</FriendName>

            <DeleteFriend nickname={nickname} />
        </FriendElementContainer>
    )
}