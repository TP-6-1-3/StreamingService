import styled from '@emotion/styled';
import { Theme } from '../../shared/theme';

export const HomeComponent = styled.div``;
export const HomeWrapper = styled.div``;

export const HomeLayout = styled.div`
    display: flex;
    flex-direction: row;
    gap: 30px;
    padding: 0 50px;
    padding-top: 100px;
    height: calc(100vh - 350px);
    /* overflow: scroll;
    margin-bottom: 300px; */
`
export const HomeHelloContent = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    text-align: left;
`
export const HomeActionContainer = styled.div`
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    gap: 20px;
`
export const HomeActionButton = styled.button`
    background: ${Theme.primary};
    color: ${Theme.whiteText};
    border-radius: 4px;
    padding: 15px;
    width: 350px;
    font-size: 14px;
    font-weight: 600;
    border: 0;
    cursor: pointer;
`
export const HomeMusicContent = styled.div``
export const HomeHeaderFirstText = styled.div`
    font-size: 60px;
    margin: 0;
    font-weight: 700;
`
export const HomeHeaderSecondText = styled.div`
    font-size: 40px;
    margin: 0;
    font-weight: 700;
`
export const MusicPublishContainer = styled.div`
    width: 500px;
    height: 500px;
    background: ${Theme.primary};
    padding: 15px;
`

export const MusicPublishImageContainer = styled.div`
    margin-top: 10px;
    overflow: hidden;
    height: calc(500px - 120px);

    img {
        width: 100%;
    }
`

export const MusicPublishPlayerActions = styled.div`
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    margin-top: 20px;
    align-items: center;
`
export const MusicPublishPlayerSlider = styled.div`
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    gap: 15px;
`

export const ProfileDataContainer = styled.div`
    margin: 0 auto;
`
export const FriendHeaderText = styled.div`
    font-size: 50px;
    font-weight: bold;
    margin-bottom: 15px;
`
export const ProfileContentContainer = styled.div`
    background: ${Theme.primary};
    color: ${Theme.whiteText};
    box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
    border-radius: 4px;
    padding: 35px;
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 25px;
`

export const AvatarContainer = styled.div`
    width: 100px;
    height: 100px;
    overflow: hidden;
    border-radius: 50%;
`
export const AvatarImg = styled.img`
    width: 100%;
`

export const FriendsContentContainer = styled.div`
    display: grid;
    grid-template-columns: 500px 1fr;
    gap: 50px;
    text-align: left;
`
export const FriendsListContainer = styled.div``
export const FriendsAddContainer = styled.div``

export const FriendsList = styled.div`
    display: flex;
    flex-direction: column;
    gap: 10px;
`
export const FriendAddText = styled.div`
    font-size: 25px;
    font-weight: bold;
    margin: 15px 0;
`
export const FriendAddContent = styled.div`
    background: ${Theme.primary};
    padding: 15px;
    display: flex;
    flex-direction: row;
    align-items: center;
    gap: 15px;
`

export const FriendAddInput = styled.div``
export const FriendErrorText = styled.div`
    width: 300px;
`