import { Moment } from 'moment';
import { IProduct } from 'app/shared/model//product.model';

export interface ICompany {
    id?: number;
    name?: string;
    createdOn?: Moment;
    updatedOn?: Moment;
    active?: boolean;
    products?: IProduct[];
}

export class Company implements ICompany {
    constructor(
        public id?: number,
        public name?: string,
        public createdOn?: Moment,
        public updatedOn?: Moment,
        public active?: boolean,
        public products?: IProduct[]
    ) {
        this.active = this.active || false;
    }
}
