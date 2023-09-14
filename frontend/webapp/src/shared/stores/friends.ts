import { createEvent, createStore } from "effector";
import { IFriendsListRequest } from "../api/friends/getFriends";

export const $friendsList = createStore<IFriendsListRequest | null>(null);
export const setFriendsListFx = createEvent<IFriendsListRequest | null>();
$friendsList.on(setFriendsListFx, (_, friendsList: IFriendsListRequest | null) => friendsList);