import { HeaderWrapper } from "../../entities/headerWrapper"
import { RegForm } from "../../features/regForm"
import { HeaderText } from "../../shared/text/headerText"
import { RegComponent, RegContent, RegFormWrapper, RegHeaderData, RegLayout } from "./styled"

export const RegPage = (): React.ReactElement<void, string> => {
    return (
        <RegComponent>
             <HeaderWrapper>
                <RegHeaderData>
                    <HeaderText>Регистрация</HeaderText>
                </RegHeaderData>
            </HeaderWrapper>

            <RegLayout>
                <RegContent>
                    <RegFormWrapper>
                        <RegForm />
                    </RegFormWrapper>
                </RegContent>
            </RegLayout>
        </RegComponent>
    )
}