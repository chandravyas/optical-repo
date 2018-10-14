import { Moment } from 'moment';

export interface IInvoice {
    id?: number;
    code?: string;
    date?: Moment;
    details?: string;
    paymentDate?: Moment;
    paymentAmount?: number;
}

export class Invoice implements IInvoice {
    constructor(
        public id?: number,
        public code?: string,
        public date?: Moment,
        public details?: string,
        public paymentDate?: Moment,
        public paymentAmount?: number
    ) {}
}
