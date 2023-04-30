import { createAction, props } from '@ngrx/store';
import { GroupModel } from 'src/app/models/group.model';

const SetGroupAction = createAction(
    '[Group] Set',
    props<GroupModel>()
);

const ResetGroupAction = createAction('[Group] Reset');

export const GroupAcions = {
    SetGroupAction,
    ResetGroupAction
}