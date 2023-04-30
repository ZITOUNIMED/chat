import { Message } from "src/app/models/message.model";
import { NotificationModel } from "src/app/models/notification.model";

export interface PostState {
    selectedGroupMessages: Message[];
    generalMessages: Message[];
    notifications: NotificationModel[];
}

export const initialState: PostState = {
    selectedGroupMessages: [],
    generalMessages: [],
    notifications: []
};