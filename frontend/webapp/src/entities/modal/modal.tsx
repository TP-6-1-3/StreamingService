import React from 'react'
import {ModalWrapper} from "./styled";


export interface IModal {
    children: React.ReactElement,
}
export const Modal = ({ children }: IModal): React.ReactElement<IModal, string> => {
    return (
        <ModalWrapper>
            {children}
        </ModalWrapper>
    )
}