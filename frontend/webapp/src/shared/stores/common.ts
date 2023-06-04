import { createEvent, createStore } from "effector";

export const $headerText = createStore<string>("Главная");
export const setHeaderTextFx = createEvent<string>();
$headerText.on(setHeaderTextFx, (_, header: string) => header);