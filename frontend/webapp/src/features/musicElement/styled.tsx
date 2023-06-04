import styled from '@emotion/styled'
import { Theme } from '../../shared/theme'

export const MusicElementContainer = styled.div`
    background: ${Theme.primary};
    padding: 10px;
    width: 100%;
`
export const MusicElementContent = styled.div`
    display: flex;
    flex-direction: row;
    align-items: center;
    gap: 25px;
    padding: 0 25px;
`
export const MusicElementImageContainer = styled.div`
    width: 60px;
    height: 60px;

    img {
        width: 100%;
    }
`
export const MusicElementName = styled.div`
    flex: 1;
    font-size: 20px;
`
export const MusicElementActions = styled.div``