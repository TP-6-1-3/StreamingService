import React from 'react';
import { MusicElementMoreIcon } from '../../entities/icons/musicElementMore';
import { MusicFooterPlayerPlayIcon } from '../../entities/icons/musicFooterPlayerPlay';
import { MusicElementActions, MusicElementContainer, MusicElementContent, MusicElementImageContainer, MusicElementName } from './styled';

export const MusicElement = () => {
    return (
        <MusicElementContainer>
            <MusicElementContent>
                <MusicFooterPlayerPlayIcon />

                <MusicElementImageContainer>
                    <img src="https://crosti.ru/patterns/00/17/01/73_picture_e6ef3066.jpg" alt="" />
                </MusicElementImageContainer>

                <MusicElementName>Refused - New Noise</MusicElementName>

                <MusicElementActions>
                    <MusicElementMoreIcon />
                </MusicElementActions>
            </MusicElementContent>
        </MusicElementContainer>
    )
}