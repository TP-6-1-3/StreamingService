import styled from '@emotion/styled'
import { Theme } from '../../shared/theme'

export const FriendElementContainer = styled.div`
    background: ${Theme.primary};
    color: ${Theme.whiteText};
    padding: 15px;

    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    gap: 15px;
`

export const AvatarContainer = styled.div`
    width: 50px;
    height: 50px;
    overflow: hidden;
    border-radius: 50%;
`
export const AvatarImg = styled.img`
    width: 100%;
`

export const FriendName = styled.div`
    flex: 1;
    text-align: left;
    font-size: 20px;
`

export const DeleteFriendContainer = styled.div`
    cursor: pointer;
`