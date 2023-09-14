import styled from '@emotion/styled';
import { Theme } from '../../shared/theme';

export const AuthFormContainer = styled.div``;

export const RefLinks = styled.div`
    margin-top: 40px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: flex-start;

    a {
        color: ${Theme.whiteText};
        font-weight: 400;
        border-bottom: 1px solid ${Theme.whiteText} !important;
        text-decoration: none !important;
        cursor: pointer;
    }

    h3 {
        margin: 5px 0;
    }

    > * {
        font-size: 14px !important;
        text-align: left !important;
    }
`;
