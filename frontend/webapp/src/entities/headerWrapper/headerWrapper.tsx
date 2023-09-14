import { HeaderWrapperContainer, HeaderWrapperContent } from "./styled"

interface IHeader {
    children: React.ReactElement;
}

export const HeaderWrapper = ({ children }: IHeader): React.ReactElement<IHeader, string> => {
    return <HeaderWrapperContainer><HeaderWrapperContent><div style={{ padding: '10px 20px', width: '100%' }}>{children}</div></HeaderWrapperContent></HeaderWrapperContainer>
}