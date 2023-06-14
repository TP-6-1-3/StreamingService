import React from 'react';
import {$userData, IProfileCredentials, IUserCredentials, IUserCredentialsRole} from "../stores/user";
import {useNavigate} from "react-router-dom";
import {useStore} from "effector-react";

interface IPrivateRouteProps {
    // userProfile: IProfileCredentials | null
    element: React.ReactElement<void, string>
}


export const isAuthenticated = (userCred: IProfileCredentials): boolean => {
    if(userCred) {
        return true
    }

    return false;
}
export const isAdmin = (profile: IProfileCredentials): boolean => {
    return profile.roles.some((role) => role.authority === 'ADMIN');
}

export const PrivateRoute = ({element}: IPrivateRouteProps)  => {
    const navigate = useNavigate();
    const userProfile = useStore($userData);

    console.log(userProfile);

    if(!userProfile) {
        return null;
    }

    if(!isAuthenticated(userProfile) && !isAdmin(userProfile)) {
        navigate("/auth")
        return null;
    }

    if(!isAdmin(userProfile)) {
        navigate("/")
    }

    return element;
}