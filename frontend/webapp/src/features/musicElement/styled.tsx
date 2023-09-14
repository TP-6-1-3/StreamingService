import styled from '@emotion/styled'
import { Theme } from '../../shared/theme'

export const MusicElementContainer = styled.div<{ active: boolean }>`
    background: ${({ active }) => active ? '#0b4c81' : Theme.primary};
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
export const MusicElementActions = styled.div`
  position: relative;
`