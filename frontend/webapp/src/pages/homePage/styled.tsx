import styled from '@emotion/styled';
import { Theme } from '../../shared/theme';

export const HomeComponent = styled.div``;
export const HomeWrapper = styled.div``;

export const HomeLayout = styled.div`
    display: flex;
    flex-direction: row;
    gap: 30px;
    padding: 0 50px;
    padding-top: 100px;
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
export const HomeMusicContent = styled.div``
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
    width: 500px;
    height: 500px;
    background: ${Theme.primary};
    padding: 15px;
`

export const MusicPublishImageContainer = styled.div`
    margin-top: 10px;
    overflow: hidden;
    height: calc(500px - 120px);

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