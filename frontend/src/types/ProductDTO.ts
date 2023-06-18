import { Category } from "./category";

export type ProductDTO = {
    id : number;
    name : string;
    description : string;
    price : number;
    imgUrl : string;
    date : string;
    categories : Category[];
}