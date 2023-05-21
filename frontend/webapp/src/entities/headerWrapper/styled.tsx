import styled from '@emotion/styled';
import { Theme } from '../../shared/theme';

export const HeaderWrapperContainer = styled.div`
    width: 100vw;
    height: 80px;
    background: ${Theme.primary};
    box-shadow: 0px 0px 20px 0px #000000BF;
    position: relative;
    z-index: 2;
`;

export const HeaderWrapperContent = styled.div`
    padding: 10px;
    height: calc(100% - 20px);
    display: flex;
    flex-direction: row;
    align-items: center;
`;