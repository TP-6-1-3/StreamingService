import React from 'react';
import { Alert, CircularProgress } from "@mui/material"
import { useNavigate, useParams } from "react-router-dom"
import { HeaderWrapper } from "../../entities/headerWrapper"
import { HeaderText } from "../../shared/text/headerText"
import { VerifyPageComponent, VerifyPageContent, VerifyPageHeaderData, VerifyPageLayout } from "./styled"
import { VerifyCodeRequest } from '../../shared/api/auth/verify';

export const VerifyPage = (): React.ReactElement<void, string> => {
    const navigate = useNavigate();
    const params = useParams();
    const code = params.code;
    const [isAccepted, setIsAccepted] = React.useState(false);
    const [errorMessage, setErrorMessage] = React.useState(false);
    const [loading, setLoading] = React.useState(true);

    React.useEffect(() => {
        if (code && loading) {
            VerifyCodeRequest(code)
                .then((data: any) => {
                    setLoading(false);
                    if (data.message) {
                        setErrorMessage(data.message);
                    } else {
                        setIsAccepted(true);
                        setTimeout(() => {
                            navigate("/");
                        }, 3000);
                    }
                })
        }
    }, [code, loading]);

    const loadingRender = <CircularProgress />;
    const stateRender = !loading ? isAccepted ? (
        <Alert variant="filled" severity="success">
            Код успешно активирован!
        </Alert>
    ) : (
        <Alert variant="filled" severity="error">
            {errorMessage}
        </Alert>
    ) : loadingRender;

    return (
        <VerifyPageComponent>
            <HeaderWrapper>
                <VerifyPageHeaderData>
                    <HeaderText>Верификация аккаунта</HeaderText>
                </VerifyPageHeaderData>
            </HeaderWrapper>

            <VerifyPageLayout>
                <VerifyPageContent>
                    {stateRender}
                </VerifyPageContent>
            </VerifyPageLayout>
        </VerifyPageComponent>
    )
}