import { Moment } from 'moment';
import { IOrderItem } from 'app/shared/model//order-item.model';
import { ICustomer } from 'app/shared/model//customer.model';

export const enum OrderStatus {
    COMPLETED = 'COMPLETED',
    PENDING = 'PENDING',
    CANCELLED = 'CANCELLED'
}

export interface IProductOrder {
    id?: number;
    quantity?: number;
    totalCost?: number;
    status?: OrderStatus;
    placedDate?: Moment;
    invoiceId?: number;
    orderitems?: IOrderItem[];
    customer?: ICustomer;
}

export class ProductOrder implements IProductOrder {
    constructor(
        public id?: number,
        public quantity?: number,
        public totalCost?: number,
        public status?: OrderStatus,
        public placedDate?: Moment,
        public invoiceId?: number,
        public orderitems?: IOrderItem[],
        public customer?: ICustomer
    ) {}
}
