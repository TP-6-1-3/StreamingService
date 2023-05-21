import { HeaderWrapperContainer, HeaderWrapperContent } from "./styled"

interface IHeader {
    children: React.ReactElement;
}

export const HeaderWrapper = ({ children }: IHeader): React.ReactElement<IHeader, string> => {
    return <HeaderWrapperContainer><HeaderWrapperContent>{children}</HeaderWrapperContent></HeaderWrapperContainer>
}