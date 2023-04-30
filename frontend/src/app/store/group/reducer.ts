import { createReducer, on } from '@ngrx/store';
import { GroupAcions } from './actions';
import { initialState, GroupState } from './group.state';
import { GroupModel } from 'src/app/models/group.model';

export const groupReducer = createReducer(
  initialState,
  on(GroupAcions.SetGroupAction, setGroup),
  on(GroupAcions.ResetGroupAction, resetGroup),
);

function setGroup(state: GroupState, group: GroupModel): GroupState {
    return {
      ...state,
      ...group
    };
}

function resetGroup(_: GroupState): GroupState {
  return initialState;
}