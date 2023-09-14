import React from 'react';
import Button from '@mui/material/Button';
import { AxiosResponse } from 'axios';
import { Alert, AlertTitle, TextField } from "@mui/material"
import { Box } from "@mui/system"
import { AuthFormContainer, RefLinks } from "./styled"
import { Link, useNavigate } from 'react-router-dom'
import { AuthRequest } from '../../shared/api/auth/login';
import { IUserCredentials, setUserCredentialsFx } from '../../shared/stores/user';
import { IRegData, RegRequest } from '../../shared/api/auth/reg';

export const RegForm = () => {
    const navigate = useNavigate();
    const [sendAuthData, setSendAuthData] = React.useState<IRegData | any>({});
    const [errors, setErrors] = React.useState([]);

    const onRegister = () => {
        RegRequest(sendAuthData)
            .then((data: any) => {
                if (!data.accessToken) {
                    const errorMessages: any = Object.values(data);
                    setErrors(errorMessages);
                } else {
                    setUserCredentialsFx(data);
                    navigate("/auth");
                }
            });
    }

    const onChange = (e: any, param: string) => {
        const value = e.currentTarget.value;
        const authData = Object.assign({}, sendAuthData, { [param]: value });

        console.log(authData);

        setSendAuthData(authData);
        setErrors([]);
    }

    const hasErrors = errors.length;
    const renderErrors = hasErrors ? (
        <Alert severity="error">
            <AlertTitle>Ошибка!</AlertTitle>
            {
                errors.map(error => error)
            }
        </Alert>
    ) : null

    return (
        <AuthFormContainer>
            {renderErrors}

            <Box
                component="form"
                sx={{
                    '& .MuiTextField-root': { m: 2, width: '240px' },
                }}
                noValidate
                autoComplete="off"
            >
                <div>
                    <TextField
                        required
                        id="outlined-required"
                        label="Адрес электронной почты"
                        defaultValue=""
                        autoComplete='off'
                        onChange={e => onChange(e, "email")}
                    />
                    <TextField
                        id="outlined-password-input"
                        label="Имя"
                        defaultValue=""
                        autoComplete='off'
                        onChange={e => onChange(e, "firstName")}
                    />
                    <TextField
                        required
                        id="outlined-required"
                        label="Фамилия"
                        defaultValue=""
                        autoComplete='off'
                        onChange={e => onChange(e, "lastName")}
                    />
                    <TextField
                        id="outlined-password-input"
                        label="Имя пользователя"
                        defaultValue=""
                        autoComplete='off'
                        onChange={e => onChange(e, "nickname")}
                    />
                    <TextField
                        required
                        id="outlined-required"
                        label="Пароль"
                        type="password"
                        autoComplete='off'
                        onChange={e => onChange(e, "password")}
                    />
                    <TextField
                        id="outlined-password-input"
                        label="Подтверждение пароля"
                        type="password"
                        autoComplete='off'
                        onChange={e => onChange(e, "repeatPassword")}
                    />
                    <Button variant="contained" size="large" onClick={onRegister}>Создать аккаунт</Button>
                </div>
            </Box>

            <RefLinks>
                <h3>Уже зарегистрированы? <Link to="/auth">Войти</Link></h3>
            </RefLinks>
        </AuthFormContainer>
    )
}