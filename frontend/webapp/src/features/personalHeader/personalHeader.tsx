import { useStore } from 'effector-react';
import React from 'react';
import {Link, useNavigate} from "react-router-dom"
import { HeaderWrapper } from "../../entities/headerWrapper"
import { $headerText } from '../../shared/stores/common';
import {$userData, IProfileCredentials} from '../../shared/stores/user';
import { HeaderText } from "../../shared/text/headerText"
import { SearchInput } from "../searchInput"
import { AvatarContainer, AvatarImg, MenuContainer, MenuElement, PersonalHeaderData } from "./styled"
import Cookies from "universal-cookie";
import {isAdmin, isAuthenticated} from "../../shared/libs/PrivateRoute";

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

    const renderAdminButton = () => {
        return isAuthenticated(userData as IProfileCredentials) && isAdmin(userData as IProfileCredentials) ? <Link to="/adminsongs"><MenuElement>Админ-панель</MenuElement></Link>  : null;
    }

    const renderMenu = showMenu ? (
        <MenuContainer>
            {renderAdminButton()}
            <Link to="/"><MenuElement>Главная</MenuElement></Link>
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