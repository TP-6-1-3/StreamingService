import styled from '@emotion/styled';
import { Theme } from '../../shared/theme';

export const GenreElementContainer = styled.div`
  background: ${Theme.primary};
  padding: 10px;
  width: 100%;
  cursor: pointer
`

export const GenreElementContent = styled.div`
    display: flex;
    flex-direction: row;
    align-items: center;
    gap: 25px;
    padding: 0 25px;
`

export const GenreElementName = styled.div`
    flex: 1;
    font-size: 20px;
`