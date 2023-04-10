import { createAction, props } from '@ngrx/store';
import { User } from '../../../models/user.model';

const SetUserAction = createAction(
    '[User] Set',
    props<User>()
);

const ResetUserAction = createAction('[User] Reset');

const ConnectAction = createAction(
    '[User] Connect',
    props<{isConnected: boolean}>()
);

export const UserAcions = {
    SetUserAction,
    ResetUserAction,
    ConnectAction
}