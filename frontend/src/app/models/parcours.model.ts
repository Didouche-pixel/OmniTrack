export interface Parcours {
    id: number;
    journeyType: string;
    status: string;
    channel: string;
    createdAt: Date;
    interruptedAt: Date | null;
    resumedAt: Date | null;
    completedAt: Date | null;
}