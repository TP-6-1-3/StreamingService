import styled from '@emotion/styled';
import { Theme } from '../../shared/theme';

export const VerifyPageComponent = styled.div``;

export const VerifyPageHeaderData = styled.div`
    display: flex;
    flex-direction: row;
    justify-content: center;
    width: 100%;
`;

export const VerifyPageLayout = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    height: calc(100% - 80px);
    min-height: 100vh;
`;
export const VerifyPageContent = styled.div`
    width: 800px;
    margin: 0 auto;
`;

export const VerifyPageFormWrapper = styled.div`
    background: ${Theme.primary};
    padding: 30px;
    border-radius: 4px;
    width: 300px;
    margin: 0 auto;
`;