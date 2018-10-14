import { IProduct } from 'app/shared/model//product.model';
import { IPrescription } from 'app/shared/model//prescription.model';
import { IProductOrder } from 'app/shared/model//product-order.model';

export interface IOrderItem {
    id?: number;
    quantity?: number;
    totalPrice?: number;
    discount?: number;
    finalPrice?: number;
    product?: IProduct;
    prescription?: IPrescription;
    productorder?: IProductOrder;
}

export class OrderItem implements IOrderItem {
    constructor(
        public id?: number,
        public quantity?: number,
        public totalPrice?: number,
        public discount?: number,
        public finalPrice?: number,
        public product?: IProduct,
        public prescription?: IPrescription,
        public productorder?: IProductOrder
    ) {}
}
