export type User {
    uid?: number;
    name: string;
    password?: string;
    status: Status;
    roles: string[];
    createTime?: Date;
    updateTime?: Date;
  }
  