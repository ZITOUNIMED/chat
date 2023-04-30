import { createReducer, on } from '@ngrx/store';
import { User } from 'src/app/models/user.model';
import { UserAcions } from './actions';
import { initialState, UserState } from './user.state';

export const userReducer = createReducer(
  initialState,
  on(UserAcions.SetUserAction, setUser),
  on(UserAcions.ResetUserAction, resetUser),
  on(UserAcions.ConnectAction, connectUser),
);

function setUser(state: UserState, user: User): UserState {
    return {
      ...state,
      ...user
    };
}

function connectUser(state: UserState, connection: {isConnected: boolean}): UserState {
  return {
    ...state,
    ...connection
  };
}

function resetUser(_: UserState): UserState {
    return initialState;
}