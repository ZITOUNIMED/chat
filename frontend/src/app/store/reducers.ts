import { ActionReducerMap } from "@ngrx/store";
import { AppState } from "./app.state";
import { userReducer } from "./user/reducer";
import { groupReducer } from "./group/reducer";
import { postReducer } from "./post/reducer";

export const appReducer: ActionReducerMap<AppState> = {
  user: userReducer,
  group: groupReducer,
  post: postReducer
}