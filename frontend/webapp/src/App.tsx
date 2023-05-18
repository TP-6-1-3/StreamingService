// import React from 'react';
import {
	createBrowserRouter,
	RouterProvider,
} from "react-router-dom";
import './App.css';
import { HomePage } from "./pages/homePage";
import { TracksPage } from "./pages/tracksPage";

const App = () => {
	const router = createBrowserRouter([
		{
			path: "/",
			element: <HomePage/>,
		},
		{
			path: "/tracks",
			element: <TracksPage/>,
		},
	]);

	return (
		<div className="App">
			<RouterProvider router={router} />
		</div>
	);
}

export default App;
