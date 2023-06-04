import styled from '@emotion/styled';
import { Theme } from '../../shared/theme';

export const AvatarContainer = styled.div`
    width: 50px;
    height: 50px;
    border-radius: 50%;
    overflow: hidden;
    cursor: pointer;
`
export const AvatarImg = styled.img`
    width: 100%;
`

export const PersonalHeaderData = styled.div`
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: space-between;
    width: 100%;
    position: relative;
    padding: 0 15px;
`;

export const MenuContainer = styled.div`
    background: ${Theme.primary};
    position: absolute;
    right: 10px;
    top: 80px;
    border: 1px black solid;

    a {
        text-decoration: none;
        color: unset;
    }
`
export const MenuElement = styled.div`
    color: ${Theme.whiteText};
    padding: 15px;
`