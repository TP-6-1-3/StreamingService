import axios, { AxiosRequestConfig, AxiosResponse } from "axios"
import { projectConfig } from "../config/project"


export const request = (method: string, route: string, axiosParams: AxiosRequestConfig = {}): Promise<AxiosResponse> => {
    const API_URL = projectConfig.API_URL;
    const axiosConfig = Object.assign({
        method,
        url: `${API_URL}${route}`,
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
    }, axiosParams) as AxiosRequestConfig;

    return axios<any>(axiosConfig).then(res => {
        return res.data;
    }).catch((err) => {
        return err.response.data;
    })
}