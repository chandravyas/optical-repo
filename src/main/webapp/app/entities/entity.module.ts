import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { OpticalshopallProductModule } from './product/product.module';
import { OpticalshopallCompanyModule } from './company/company.module';
import { OpticalshopallQualityModule } from './quality/quality.module';
import { OpticalshopallSupplierModule } from './supplier/supplier.module';
import { OpticalshopallCustomerModule } from './customer/customer.module';
import { OpticalshopallPrescriptionModule } from './prescription/prescription.module';
import { OpticalshopallProductOrderModule } from './product-order/product-order.module';
import { OpticalshopallOrderItemModule } from './order-item/order-item.module';
import { OpticalshopallInvoiceModule } from './invoice/invoice.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        OpticalshopallProductModule,
        OpticalshopallCompanyModule,
        OpticalshopallQualityModule,
        OpticalshopallSupplierModule,
        OpticalshopallCustomerModule,
        OpticalshopallPrescriptionModule,
        OpticalshopallProductOrderModule,
        OpticalshopallOrderItemModule,
        OpticalshopallInvoiceModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OpticalshopallEntityModule {}
