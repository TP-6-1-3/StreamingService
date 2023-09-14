import { request } from "../../libs/request"

interface ISingersFields {
    fullName: string,
    description: string
}
export const AddSingerRequest = (singer: ISingersFields) => {
    return request('POST', `/singers`, {data: singer})
}