import React from 'react';
import { GetSongsRequest } from '../../shared/api/songs/getSongs';
import { setTracksListFx } from '../../shared/stores/tracks';
import { SearchInputContainer, SearchInputElement } from './styled';

export default function useDebouncedFunction(func: any, delay: number, cleanUp = false) {
    const timeoutRef = React.useRef<any>();

    function clearTimer() {
        if (timeoutRef.current) {
            clearTimeout(timeoutRef.current);
            timeoutRef.current = undefined;
        }
    }

    React.useEffect(() => (cleanUp ? clearTimer : undefined), [cleanUp]);

    return (...args: any) => {
        clearTimer();
        timeoutRef.current = setTimeout(() => func(...args), delay);
    };
}

export const SearchInput = () => {
    const [value, setValue] = React.useState();
    const valueLogging = (value: string) => {
        GetSongsRequest({
            count: 20,
            isAsc: false,
            title: value
        }).then(musicData => {
            if (musicData) {
                const musicList = musicData.data;
                setTracksListFx(musicList);
            }
        })
    }

    const debouncedValueLogging = useDebouncedFunction(valueLogging, 300);

    const handleChange = (event: any) => {
        const newValue = event.currentTarget.value;

        console.log(newValue, newValue.length);

        setValue(newValue);
        
        // if (newValue.length > 0) {
            debouncedValueLogging(newValue);
        // } else {
        //     // valueLogging(va)
        // }
    };

    return (
        <SearchInputContainer>
            <svg width="18" height="18" viewBox="0 0 18 18" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M12.5 11H11.71L11.43 10.73C12.41 9.59 13 8.11 13 6.5C13 2.91 10.09 0 6.5 0C2.91 0 0 2.91 0 6.5C0 10.09 2.91 13 6.5 13C8.11 13 9.59 12.41 10.73 11.43L11 11.71V12.5L16 17.49L17.49 16L12.5 11ZM6.5 11C4.01 11 2 8.99 2 6.5C2 4.01 4.01 2 6.5 2C8.99 2 11 4.01 11 6.5C11 8.99 8.99 11 6.5 11Z" fill="white" />
            </svg>

            <SearchInputElement onChange={handleChange} placeholder='Поиск...' />
        </SearchInputContainer>
    )
}