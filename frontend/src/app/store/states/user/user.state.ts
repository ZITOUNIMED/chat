export interface UserState {
    name: string;
    username: string;
    uuid: string;
    isConnected: boolean;
    token?: string;
}

export const initialState: UserState = {
    name: '',
    username: '',
    uuid: '',
    isConnected: false,
    token: ''
};