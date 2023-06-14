import {MusicElementOptionsContainer, MusicElementOptionsItem} from "./styled";


interface IMenu {
    children: React.ReactElement,
    songId?: number
}

export const MusicElementOptions = (props : IMenu): React.ReactElement<IMenu, string> => {

    return (
        <MusicElementOptionsContainer>
            <div>
                {props.children}
            </div>
        </MusicElementOptionsContainer>
    )
}