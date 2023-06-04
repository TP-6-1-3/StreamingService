import React from 'react';
import Button from '@mui/material/Button';
import Cookies from 'universal-cookie';
import { AxiosResponse } from 'axios';
import { Alert, AlertTitle, TextField } from "@mui/material"
import { Box } from "@mui/system"
import { AuthFormContainer, RefLinks } from "./styled"
import { Link, useNavigate } from 'react-router-dom'
import { AuthRequest } from '../../shared/api/auth/login';
import { IUserCredentials, setUserCredentialsFx } from '../../shared/stores/user';

export const AuthForm = () => {
    const navigate = useNavigate();
    const cookies = new Cookies();
    const [email, setEmail] = React.useState("");
    const [password, setPassword] = React.useState("");
    const [errors, setErrors] = React.useState([]);

    const onLogin = () => {
        AuthRequest(email, password)
        .then((data: any) => {
            if (!data.accessToken) {
                const errorMessages: any = Object.values(data);
                setErrors(errorMessages);
            } else {
                const { accessToken, refreshToken } = data;
                cookies.set('accessToken', accessToken, { path: '/' });
                cookies.set('refreshToken', refreshToken, { path: '/' });
                
                setUserCredentialsFx(data);
                navigate("/");
            }
        });
    }

    const onChange = (e: any, fx: React.Dispatch<React.SetStateAction<string>>) => {
        fx(e.currentTarget.value);
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
            { renderErrors }

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
                        label="Логин"
                        defaultValue=""
                        autoComplete='off'
                        onChange={e => onChange(e, setEmail)}
                    />
                    <TextField
                        id="outlined-password-input"
                        label="Пароль"
                        type="password"
                        autoComplete='off'
                        onChange={e => onChange(e, setPassword)}
                    />
                    <Button variant="contained" size="large" onClick={onLogin}>Войти</Button>
                </div>
            </Box>

            <RefLinks>
                <Link to="#">Забыли пароль?</Link>
                <h3>Новый пользователь? <Link to="/reg">Зарегистрироваться</Link></h3>
            </RefLinks>
        </AuthFormContainer>
    )
}