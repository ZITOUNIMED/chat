import { ActionReducerMap } from "@ngrx/store";
import { AppState } from "./states/app.state";
import { userReducer } from "./states/user/reducer";
import { groupReducer } from "./states/group/reducer";

export const appReducer: ActionReducerMap<AppState> = {
  user: userReducer,
  group: groupReducer,
}