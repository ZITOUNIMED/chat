import { ActionReducerMap } from "@ngrx/store";
import { AppState } from "./states/app.state";
import { userReducer } from "./states/user/reducer";

export const appReducer: ActionReducerMap<AppState> = {
  user: userReducer
}