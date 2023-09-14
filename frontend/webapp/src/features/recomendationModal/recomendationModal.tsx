import React, {useEffect, useState} from 'react'
import {IModal, Modal} from "../../entities/modal";
import {CloseButton, Form, ModalContent, Title, Label, Input, Button} from "../../entities/modal/styled";
import {useStore} from "effector-react";
import {$modalIsOpen, $setModalOpenFx} from "../../shared/stores/modal";
import {$recomend} from "../../shared/stores/recomend";
import {AddRecomendation} from "../../shared/api/recomend/addRecomend";

interface IRecomendationModal {
    close: Function;

}

export const RecomendationModal = (props: IRecomendationModal): React.ReactElement<IRecomendationModal, string> => {
    const modalIsOpen = useStore($modalIsOpen);
    const recomend = useStore($recomend);

    const [nickname, setNickname] = useState("");

    const handleNicknameChange = (event: any) => {
        setNickname(event.target.value);
    };

    const onModalClose = () => {
        $setModalOpenFx(false)
    }

    console.log("r " + recomend);

    const onHandleSubmit = () => {
        AddRecomendation(nickname, recomend)
        onModalClose();
    }

    return (
        <Modal>
            <ModalContent>
                <CloseButton onClick={onModalClose}>&times;</CloseButton>
                <Title>Рекомендовать трек друзьям</Title>

                <Form onSubmit={onHandleSubmit}>
                    <Label htmlFor="nickname">Ник друга:</Label>
                    <Input type="text" id="nickname" name="nickname" value={nickname} onChange={handleNicknameChange} required />

                    <Button type="submit">Отправить</Button>
                </Form>
            </ModalContent>
        </Modal>
    )
}