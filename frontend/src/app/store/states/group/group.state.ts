export interface GroupState {
    uuid: string;
    users: any[];
	name: string;
    adminUuid: string;
    creatorUuid: string;
    createdAt: number;
}

export const initialState: GroupState = {
    uuid: '',
    users: [],
	name: '',
    adminUuid: '',
    creatorUuid: '',
    createdAt: 0,
};