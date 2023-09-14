import { createEvent, createStore } from "effector";
import { projectConfig } from "../config/project";


export const $modalIsOpen = createStore<boolean>(false);
export const $setModalOpenFx = createEvent<boolean>();

$modalIsOpen.on($setModalOpenFx, (_, isOpen) => isOpen);