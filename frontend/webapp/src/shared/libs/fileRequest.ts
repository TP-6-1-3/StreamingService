import axios, { AxiosRequestConfig, AxiosResponse } from "axios"
import { projectConfig } from "../config/project"
import { $userCredentials } from "../stores/user";

export const fileRequest = <T>(method: string, route: string, axiosParams: AxiosRequestConfig = {}): Promise<T> => {
    const userCredentials = $userCredentials.getState();

    const authHeader = userCredentials?.accessToken ? {
        'Authorization': `Bearer ${userCredentials.accessToken}`
    } : {};

    const API_URL = projectConfig.API_URL;
    const axiosConfig = Object.assign({
        method,
        url: `${API_URL}${route}`,
        headers: Object.assign(authHeader, {
            'Content-Type': 'multipart/form-data',
        }),
    }, axiosParams) as AxiosRequestConfig;

    return axios<any>(axiosConfig).then(res => {
        return res.data;
    }).catch((err) => {
        return err.response.data;
    })
}