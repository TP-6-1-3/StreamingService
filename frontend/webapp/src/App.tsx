// import React from 'react';
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

const App = () => {
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
