import { createAction, props } from '@ngrx/store';
import { Message } from 'src/app/models/message.model';
import { PostInitMessage } from '../payloads/post-init-messages.model';

const SetNewPostAction = createAction(
    '[Post] Set',
    props<Message>()
);

const InitMessagesAction = createAction(
    '[Post] Init Messages',
    props<PostInitMessage>()
);

const ResetPostsAction = createAction('[Post] Reset');

export const PostAcions = {
    SetNewPostAction,
    InitMessagesAction,
    ResetPostsAction
}