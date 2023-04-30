import { Message } from "src/app/models/message.model";

export interface PostInitMessage {
    groupUuid: string | null;
    messages: Message[]
}