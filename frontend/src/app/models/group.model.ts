export interface GroupModel {
    uuid: string;
    users: any[];
	name: string;
    adminUuid: string;
    creatorUuid: string;
    createdAt: number;
}