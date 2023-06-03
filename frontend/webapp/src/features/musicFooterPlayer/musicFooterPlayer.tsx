import { Slider } from '@mui/material';
import { MusicFooterPlayerEqualizerIcon } from "../../entities/icons/musicFooterPlayerEqualizer"
import { MusicFooterPlayerHeartIcon } from "../../entities/icons/musicFooterPlayerHeart"
import { MusicFooterPlayerLeftIcon } from "../../entities/icons/musicFooterPlayerLeft"
import { MusicFooterPlayerPlayIcon } from "../../entities/icons/musicFooterPlayerPlay"
import { MusicFooterPlayerRightIcon } from "../../entities/icons/musicFooterPlayerRight"
import { MusicFooterPlayerSlider, MusicPlayerFooterBasisActions, MusicPlayerFooterContainer, MusicPlayerFooterImageContainer, MusicPlayerFooterTrackInfo } from "./styled"

export const MusicPlayerFooter = () => {
    return (
        <MusicPlayerFooterContainer>
            <MusicPlayerFooterImageContainer>
                <img src="https://crosti.ru/patterns/00/17/01/73_picture_e6ef3066.jpg" alt="" />
            </MusicPlayerFooterImageContainer>

            <MusicPlayerFooterBasisActions>
                <MusicFooterPlayerLeftIcon />
                <MusicFooterPlayerPlayIcon />
                <MusicFooterPlayerRightIcon />
            </MusicPlayerFooterBasisActions>

            <MusicPlayerFooterTrackInfo>
                <span>Исполнитель</span>
                <span>Композиция</span>
            </MusicPlayerFooterTrackInfo>

            <MusicFooterPlayerHeartIcon />
            <MusicFooterPlayerEqualizerIcon />

            <MusicFooterPlayerSlider>
                <Slider />
            </MusicFooterPlayerSlider>
        </MusicPlayerFooterContainer>
    )
}