import styled from '@emotion/styled';
import { Theme } from '../../shared/theme';

export const HeaderWrapperContainer = styled.div`
    /* width: calc(100vw); */
    /* height: 80px; */
    /* padding: 0 15px; */
    background: ${Theme.primary};
    /* position: relative; */
    position: sticky;
    z-index: 2;
    top: 0;
`;

export const HeaderWrapperContent = styled.div`
    /* padding: 10px; */
    /* padding: 10px;
    height: calc(100% - 20px); */
    display: flex;
    flex-direction: row;
    align-items: center;
`;

export const HeaderWrapperWrapper = styled.div`
    /* padding: 10px 20px; */
    width: 100%;
    box-shadow: 0px 0px 20px 0px #000000BF;
`;