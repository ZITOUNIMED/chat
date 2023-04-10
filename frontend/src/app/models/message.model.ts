import { User } from "./user.model";

export interface Message {
    uuid: string;
    content: string;
    timestamp: number;
    user?: User;
}