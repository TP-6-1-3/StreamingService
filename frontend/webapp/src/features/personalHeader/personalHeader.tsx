import { useStore } from 'effector-react';
import React from 'react';
import { Link } from "react-router-dom"
import { HeaderWrapper } from "../../entities/headerWrapper"
import { $headerText } from '../../shared/stores/common';
import { HeaderText } from "../../shared/text/headerText"
import { SearchInput } from "../searchInput"
import { AvatarContainer, AvatarImg, MenuContainer, MenuElement, PersonalHeaderData } from "./styled"

export const PersonalHeader = () => {
    const headerText = useStore($headerText);
    const [showMenu, setShowMenu] = React.useState(false);

    const renderMenu = showMenu ? (
        <MenuContainer>
            <Link to="/tracks"><MenuElement>Мои треки</MenuElement></Link>
            <Link to="#"><MenuElement>Профиль</MenuElement></Link>
            <Link to="#"><MenuElement>Выйти</MenuElement></Link>
        </MenuContainer >
    ) : null;

    return (
        <HeaderWrapper>
            <PersonalHeaderData>
                <HeaderText>{headerText}</HeaderText>

                <SearchInput />

                <AvatarContainer onClick={() => setShowMenu(!showMenu)}>
                    <AvatarImg src="https://crosti.ru/patterns/00/17/01/73_picture_e6ef3066.jpg" alt="" />
                </AvatarContainer>

                {renderMenu}
            </PersonalHeaderData>
        </HeaderWrapper>
    )
}