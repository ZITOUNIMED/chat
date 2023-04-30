import { GroupState } from "./group/group.state";
import { PostState } from "./post/post.state";
import { UserState } from "./user/user.state";

export interface AppState {
    user: UserState;
    group: GroupState;
    post: PostState
}