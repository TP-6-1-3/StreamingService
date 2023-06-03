import styled from '@emotion/styled';
import { Theme } from '../../shared/theme';

export const SearchInputContainer = styled.div`
    background: ${Theme.primary2};
    padding: 10px 20px;
    display: flex;
    flex-direction: row;
    align-items: center;
    border-radius: 4px;
`
export const SearchInputElement = styled.input`
    background: unset;
    border: unset;
    outline: unset;
    color: ${Theme.whiteText};
    margin-left: 15px;
    
`
// export const SearchInputContainer = styled.div``
// export const SearchInputContainer = styled.div``