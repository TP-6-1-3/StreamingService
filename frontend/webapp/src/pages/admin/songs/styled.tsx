import styled from '@emotion/styled';
import { Theme } from "../../../shared/theme";


export const HomeComponent = styled.div``;
export const HomeWrapper = styled.div``;

export const HomeLayout = styled.div`
    display: flex;
    flex-direction: row;
    gap: 30px;
    padding: 0 50px;
    padding-top: 100px;
    height: calc(100vh - 350px);
    /* overflow: scroll;
    margin-bottom: 300px; */
`

export const AddMusicCard = styled.div`
  display: flex;
  flex-direction: column;
  width: 500px;
  height: 500px;
  background: ${Theme.primary};
  padding: 15px;
`