import Button from '@mui/material/Button';
import { TextField } from "@mui/material"
import { Box } from "@mui/system"
import { AuthFormContainer, RefLinks } from "./styled"
import { Link } from 'react-router-dom'

export const AuthForm = () => {
    return (
        <AuthFormContainer>
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
                    />
                    <TextField
                        id="outlined-password-input"
                        label="Пароль"
                        type="password"
                        autoComplete='off'
                    />
                    <Button variant="contained" size="large">Войти</Button>
                </div>
            </Box>

            <RefLinks>
                <Link to="#">Забыли пароль?</Link>
                <h3>Новый пользователь? <Link to="/reg">Зарегистрироваться</Link></h3>
            </RefLinks>
        </AuthFormContainer>
    )
}