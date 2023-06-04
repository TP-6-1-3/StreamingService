import { HeaderWrapper } from "../../entities/headerWrapper"
import { AuthForm } from "../../features/authForm"
import { HeaderText } from "../../shared/text/headerText"
import { AuthComponent, AuthContent, AuthFormWrapper, AuthHeaderData, AuthLayout, AuthProjectInfo, AuthProjectLogo } from "./styled"

export const AuthPage = (): React.ReactElement<void, string> => {
    return (
        <AuthComponent>
            <HeaderWrapper>
                <AuthHeaderData>
                    <HeaderText>Авторизация</HeaderText>
                </AuthHeaderData>
            </HeaderWrapper>

            <AuthLayout>
                <AuthContent>
                    <AuthProjectLogo src='/assets/logo.svg' alt='' />

                    <AuthProjectInfo>Любимые треки, персональные подборки и эквалайзер в одном месте.</AuthProjectInfo>

                    <AuthFormWrapper>
                        <AuthForm />
                    </AuthFormWrapper>
                </AuthContent>
            </AuthLayout>
        </AuthComponent>
    )
}