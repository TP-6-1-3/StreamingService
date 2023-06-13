import { useStore } from 'effector-react';
import React from 'react';
import {Link, useNavigate} from "react-router-dom"
import { HeaderWrapper } from "../../entities/headerWrapper"
import { $headerText } from '../../shared/stores/common';
import { $userData } from '../../shared/stores/user';
import { HeaderText } from "../../shared/text/headerText"
import { SearchInput } from "../searchInput"
import { AvatarContainer, AvatarImg, MenuContainer, MenuElement, PersonalHeaderData } from "./styled"
import Cookies from "universal-cookie";

export const PersonalHeader = () => {
    const userData = useStore($userData);
    const headerText = useStore($headerText);
    const [showMenu, setShowMenu] = React.useState(false);

    const navigate = useNavigate();
    const cookies = new Cookies();
    const onLogout = () => {
        cookies.remove("accessToken",  { path: '/' })
        cookies.remove("refreshToken", { path: '/' });

        navigate("/");
        window.location.pathname = '/'
    }

    const renderMenu = showMenu ? (
        <MenuContainer>
            <Link to="/tracks"><MenuElement>Мои треки</MenuElement></Link>
            <Link to="/recommends"><MenuElement>Подборка</MenuElement></Link>
            <Link to="/friends"><MenuElement>Друзья</MenuElement></Link>
            <Link to="/profile"><MenuElement>Профиль</MenuElement></Link>
            <Link to="#" onClick={onLogout}><MenuElement>Выйти</MenuElement></Link>
        </MenuContainer>
    ) : null;

    const isAuthed = userData;

    return (
        <HeaderWrapper>
            <PersonalHeaderData>
                <HeaderText>{headerText}</HeaderText>

                <SearchInput />

                { isAuthed ? <AvatarContainer onClick={() => setShowMenu(!showMenu)}>
                    <AvatarImg src="/assets/emptyAvatar.png" alt="" />
                </AvatarContainer> : <div></div>}

                {renderMenu}
            </PersonalHeaderData>
        </HeaderWrapper>
    )
}