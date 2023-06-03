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
// export const MusicPlayerFooter = styled.div``
// export const MusicPlayerFooter = styled.div``