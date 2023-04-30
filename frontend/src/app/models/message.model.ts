import { GroupModel } from "./group.model";
import { User } from "./user.model";

export interface Message {
    uuid: string;
    content: string;
    group?: GroupModel;
    timestamp: number;
    user?: User;
}