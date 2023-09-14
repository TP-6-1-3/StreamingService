import React, {useEffect} from 'react';
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
import {$userCredentials, $userData, IUserCredentials, setUserCredentialsFx, setUserDataFx} from './shared/stores/user';
import { GetCredentialsRequest } from './shared/api/auth/credentials';
import { ProfilePage } from './pages/profilePage';
import { FriendsPage } from './pages/friendsPage';
import {RecommendsPage} from "./pages/recommendsPage";
import {Songs} from "./pages/admin/songs";
import {PrivateRoute} from "./shared/libs/PrivateRoute";
import {useStore} from "effector-react";
import {GenresPage} from "./pages/genresPage";
import {RecomendationModal} from "./features/recomendationModal";
import {$modalIsOpen, $setModalOpenFx} from "./shared/stores/modal";


const App = () => {
	const cookies = new Cookies();



	React.useEffect(() => {
		const accessToken = cookies.get('accessToken');
		const refreshToken = cookies.get('refreshToken');

		if (accessToken && refreshToken) {
			GetCredentialsRequest().then((userData: any) => {
				if (userData) {
					if(userData.message) return;

					setUserDataFx(userData);
				}
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
		{
			path: "/profile",
			element: <ProfilePage />,
		},
		{
			path: "/friends",
			element: <FriendsPage />,
		},
		{
			path: "/recommends",
			element: <RecommendsPage />
		},
		{
			path: "/genres",
			element: <GenresPage />
		},
		{
			path: "/adminsongs",
			element: <PrivateRoute element={<Songs/>}  />
		}
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
