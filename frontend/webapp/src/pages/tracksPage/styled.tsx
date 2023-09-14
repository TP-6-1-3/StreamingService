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
export const HomeHelloContent = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    text-align: left;
`
export const HomeActionContainer = styled.div`
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    gap: 20px;
`
export const HomeActionButton = styled.button`
    background: ${Theme.primary};
    color: ${Theme.whiteText};
    border-radius: 4px;
    padding: 15px;
    width: 350px;
    font-size: 14px;
    font-weight: 600;
    border: 0;
    cursor: pointer;
`
export const HomeMusicContent = styled.div`
    display: flex;
    flex-direction: column;
    gap: 30px;
    align-items: flex-start;
`
export const HomeHeaderFirstText = styled.div`
    font-size: 60px;
    margin: 0;
    font-weight: 700;
`
export const HomeHeaderSecondText = styled.div`
    font-size: 40px;
    margin: 0;
    font-weight: 700;
`
export const MusicPublishContainer = styled.div`
    width: 450px;
    background: ${Theme.primary};
    padding: 15px;
    /* margin: 0 auto; */
`

export const MusicPublishPlayContainer = styled.div`
    display: flex;
    flex-direction: row;
    gap: 10px;
`
export const MusicPublishPlayElement = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 10px;
    flex: 1;
`

export const MusicPublishImageContainer = styled.div`
    margin-top: 10px;
    overflow: hidden;
    width: 300px;
    height: 300px;

    img {
        width: 100%;
    }
`

export const MusicPublishPlayerActions = styled.div`
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    margin-top: 20px;
    align-items: center;
`
export const MusicPublishPlayerSlider = styled.div`
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    gap: 15px;
`

export const MusicDescription = styled.div`
    display: flex;
    flex-direction: column;
    gap: 10px;
    justify-content: flex-start;
    margin-top: 20px;
`

export const MusicDesctiptionElement = styled.div`
    display: flex;
`
export const MusicDescriptionLabel = styled.div`
    font-weight: 700;
    width: 200px;
    text-align: left;
`
export const MusicDescriptionValue = styled.div`

`

export const MusicListContainer = styled.div`
    height: calc(100vh - 150px);
    overflow: scroll;
    overflow-x: hidden;
    gap: 15px;
    display: flex;
    flex-direction: column;

    &::-webkit-scrollbar { width: 0; }
`