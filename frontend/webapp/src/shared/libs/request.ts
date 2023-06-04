import axios, { AxiosRequestConfig, AxiosResponse } from "axios"
import { projectConfig } from "../config/project"
import { $userCredentials } from "../stores/user";

export const request = <T>(method: string, route: string, axiosParams: AxiosRequestConfig = {}): Promise<T> => {
    const userCredentials = $userCredentials.getState();

    const authHeader = userCredentials?.accessToken ? {
        'Authorization': `Bearer ${userCredentials.accessToken}`
    } : {};

    const API_URL = projectConfig.API_URL;
    const axiosConfig = Object.assign({
        method,
        url: `${API_URL}${route}`,
        headers: Object.assign(authHeader, {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        }),
    }, axiosParams) as AxiosRequestConfig;

    return axios<any>(axiosConfig).then(res => {
        return res.data;
    }).catch((err) => {
        return err.response.data;
    })
}