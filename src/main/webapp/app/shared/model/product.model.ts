import { Moment } from 'moment';
import { ICompany } from 'app/shared/model//company.model';
import { IQuality } from 'app/shared/model//quality.model';
import { ISupplier } from 'app/shared/model//supplier.model';

export const enum ProductType {
    Glasses = 'Glasses',
    Goggles = 'Goggles',
    Frames = 'Frames'
}

export interface IProduct {
    id?: number;
    name?: string;
    productType?: ProductType;
    code?: string;
    description?: string;
    color?: string;
    costprice?: number;
    sellingPrice?: number;
    glassCoating?: string;
    glassDesign?: string;
    quantityAvailable?: number;
    createdOn?: Moment;
    updatedOn?: Moment;
    active?: boolean;
    barcode?: string;
    company?: ICompany;
    quality?: IQuality;
    supplier?: ISupplier;
}

export class Product implements IProduct {
    constructor(
        public id?: number,
        public name?: string,
        public productType?: ProductType,
        public code?: string,
        public description?: string,
        public color?: string,
        public costprice?: number,
        public sellingPrice?: number,
        public glassCoating?: string,
        public glassDesign?: string,
        public quantityAvailable?: number,
        public createdOn?: Moment,
        public updatedOn?: Moment,
        public active?: boolean,
        public barcode?: string,
        public company?: ICompany,
        public quality?: IQuality,
        public supplier?: ISupplier
    ) {
        this.active = this.active || false;
    }
}
