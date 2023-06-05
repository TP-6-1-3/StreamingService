import styled from '@emotion/styled'
import { Theme } from '../../shared/theme'

export const MusicPlayerFooterContainer = styled.div`
    position: fixed;
    width: calc(100vw - 60px);
    height: 50px;
    background: ${Theme.primary};
    bottom: 0;
    left: 0;
    padding: 10px 40px;
    display: flex;
    flex-direction: row;
    align-items: center;
    gap: 30px;
    box-shadow: 0px 0px 20px 0px #000000BF;
`

export const MusicPlayerFooterImageContainer = styled.div`
    width: 50px;
    height: 50px;
    overflow: hidden;

    img {
        width: 100%;
    }
`
export const MusicPlayerFooterBasisActions = styled.div`
    display: flex;
    flex-direction: row;
    gap: 30px;
    align-items: center;
`
export const MusicPlayerFooterTrackInfo = styled.div`
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    width: 200px;

    span:nth-of-type(1) {
        color: #063256;
        font-weight: 700;
    }
`
export const MusicFooterPlayerSlider = styled.div`
    flex: 1;

    .MuiSlider-rail {
        background: #000000 !important;
    }

    .MuiSlider-track {
        background: #2C98F0 !important;
    }
`
export const MusicFooterPlayerEqualizer = styled.div`

`

export const EqualizerLines = styled.div`
    height: 70px;
    display: flex;
    flex-direction: row;
    gap: 15px;
    padding: 15px 0;
`

export const MusicFooterPlayerEqualizerContainer = styled.div`
    /* background: ${Theme.primary}; */
    background: black;
    position: absolute;
    bottom: 70px;
    border: 1px black solid;
    padding: 10px;
    display: flex;
    flex-direction: column;
`
export const MusicPlayerFooterTime = styled.div`
    width: 50px;
`