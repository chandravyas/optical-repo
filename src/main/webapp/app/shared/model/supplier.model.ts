import { Moment } from 'moment';
import { IProduct } from 'app/shared/model//product.model';

export interface ISupplier {
    id?: number;
    name?: string;
    contactPerson?: string;
    contactNumber?: string;
    gstNumber?: string;
    state?: string;
    createdOn?: Moment;
    updatedOn?: Moment;
    active?: boolean;
    products?: IProduct[];
}

export class Supplier implements ISupplier {
    constructor(
        public id?: number,
        public name?: string,
        public contactPerson?: string,
        public contactNumber?: string,
        public gstNumber?: string,
        public state?: string,
        public createdOn?: Moment,
        public updatedOn?: Moment,
        public active?: boolean,
        public products?: IProduct[]
    ) {
        this.active = this.active || false;
    }
}
