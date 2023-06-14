import styled from '@emotion/styled';
import { Theme } from '../../shared/theme';

export const HomeComponent = styled.div``;
export const HomeWrapper = styled.div``;

export const HomeLayout = styled.div`
    display: grid;
    grid-template-columns: 1fr minmax(700px, auto);
    gap: 50px;
    padding: 0 50px;
    padding-top: 20px;
    height: calc(100vh - 350px);
`


export const HomeGenresContent = styled.div`
    display: flex;
    flex-direction: column;
    gap: 30px;
    align-items: flex-start;
`
export const HomeGenresTracks = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: flex-end;
`