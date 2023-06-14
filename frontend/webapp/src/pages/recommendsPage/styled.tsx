import styled from '@emotion/styled';
import { Theme } from '../../shared/theme';

export const RecomendsComponent = styled.div``

export const HomeLayout = styled.div`
    display: flex;
    flex-direction: column;
    gap: 30px;
    padding: 0 50px;
    padding-top: 100px;
    /* height: calc(100vh - 350px); */
    overflow: scroll;
    /* margin-bottom: 300px; */

    &::-webkit-scrollbar { width: 0; }
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

export const RecommendsHeaderContent = styled.div`
    display: flex;
    text-align: left;
    flex-direction: column;
`

export const RecommendsContent = styled.div`
    display: flex;
    justify-content: center;
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