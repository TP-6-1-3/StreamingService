import styled from '@emotion/styled';
import { Theme } from '../../shared/theme';

export const AuthComponent = styled.div``;

export const AuthHeaderData = styled.div`
    display: flex;
    flex-direction: row;
    justify-content: center;
    width: 100%;
`;

export const AuthLayout = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    height: calc(100vh - 80px);
`;
export const AuthContent = styled.div`
    width: 800px;
    margin: 0 auto;
`;
export const AuthProjectLogo = styled.img``;
export const AuthProjectInfo = styled.p`
    padding: 0;
    font-size: 32px;
    line-height: 1;
`;

export const AuthFormWrapper = styled.div`
    background: ${Theme.primary};
    padding: 30px;
    border-radius: 4px;
    width: 300px;
    margin: 0 auto;
`;