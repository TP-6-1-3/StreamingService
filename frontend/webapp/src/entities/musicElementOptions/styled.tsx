import styled from '@emotion/styled';
import { Theme } from '../../shared/theme';

export const MusicElementOptionsContainer = styled.div`
  position: absolute;
  top: 100%;
  right: 80%;
  background-color: ${Theme.primary};
  border-radius: 4px;
  box-shadow: 0px 0px 20px 0px #000000BF;;
  padding: 8px;
  z-index: 10;
`
export const MusicElementOptionsItemList = styled.div``
export const MusicElementOptionsItem = styled.div`
  padding: 8px;
  cursor: pointer;

  &:hover {
    background-color: ${Theme.primary2};
  }
`