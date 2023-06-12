import React from 'react';
import {
	createBrowserRouter,
	RouterProvider,
} from "react-router-dom";
import './App.css';
import { Template } from "./entities/template";
import { AuthPage } from "./pages/authPage";
import { HomePage } from "./pages/homePage";
import { RegPage } from "./pages/regPage";
import { TracksPage } from "./pages/tracksPage";
import { VerifyPage } from "./pages/verifyPage";
import Cookies from 'universal-cookie';
import { IUserCredentials, setUserCredentialsFx, setUserDataFx } from './shared/stores/user';
import { GetCredentialsRequest } from './shared/api/auth/credentials';

const App = () => {
	const cookies = new Cookies();

	React.useEffect(() => {
		const accessToken = cookies.get('accessToken');
		const refreshToken = cookies.get('refreshToken');

		if (accessToken && refreshToken) {
			GetCredentialsRequest().then((userData) => {
				if (userData) setUserDataFx(userData);
			})
		}
		setUserCredentialsFx({ accessToken, refreshToken })
	}, []);

	const router = createBrowserRouter([
		{
			path: "/",
			element: <HomePage />,
		},
		{
			path: "/tracks",
			element: <TracksPage />,
		},
		{
			path: "/auth",
			element: <AuthPage />,
		},
		{
			path: "/auth/verify/:code",
			element: <VerifyPage />,
		},
		{
			path: "/reg",
			element: <RegPage />,
		},
		
	]);

	return (
		<div className="App">
			<Template>
			<RouterProvider router={router} />
			</Template>
		</div>
	);
}

export default App;
