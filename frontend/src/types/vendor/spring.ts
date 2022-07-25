export type SpringPage<T> = {
    content: T[];
    last: boolean;
    totalPages: number;
    totalElements: number;
    first: boolean;
    number: number;
    numberOfElements?: number;
    size: number;
    empty: boolean;
}