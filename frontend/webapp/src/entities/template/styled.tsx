import styled from '@emotion/styled';
import { Theme } from '../../shared/theme';

export const TemplateContainer = styled.div`
    background: ${Theme.background};
    width: 100%;
    height: 100%;
    min-height: 100vh;
    color: ${Theme.whiteText};

    .MuiInputBase-root fieldset {
        border-color: ${Theme.whiteText} !important;
    }

    .MuiInputBase-input {
        color: ${Theme.whiteText};
    }

    .MuiFormLabel-root {
        color: ${Theme.background} !important;
    }

    .MuiButtonBase-root {
        box-shadow: unset !important;
        background: ${Theme.authButton} !important;
        text-transform: unset !important;
        font-size: 20px !important;
        padding: 10px 40px !important;
    }

    .MuiFormLabel-root {
        color: ${Theme.whiteText} !important;
    }

    .MuiLink-root {
        border-bottom: 1px solid ${Theme.whiteText} !important;
        text-decoration: none !important;
        cursor: pointer;
    }

    .MuiAlert-standardError {
        text-align: left;
    }
`;

export const TemplateContent = styled.div`
    position: relative;
    z-index: 1;
`;
export const TemplateDesignTop = styled.div`
    background: ${Theme.gradientTop};
    width: calc(100vw);
    height: 40vh;
    position: absolute;
    top: 0;
    left: 0;
`;
export const TemplateDesignBottom = styled.div`
    background: ${Theme.gradientBottom};
    width: calc(100vw);
    height: 40vh;
    position: fixed;
    bottom: 0;
    left: 0;
`;