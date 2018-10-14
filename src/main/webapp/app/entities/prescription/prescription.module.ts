import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OpticalshopallSharedModule } from 'app/shared';
import {
    PrescriptionComponent,
    PrescriptionDetailComponent,
    PrescriptionUpdateComponent,
    PrescriptionDeletePopupComponent,
    PrescriptionDeleteDialogComponent,
    prescriptionRoute,
    prescriptionPopupRoute
} from './';

const ENTITY_STATES = [...prescriptionRoute, ...prescriptionPopupRoute];

@NgModule({
    imports: [OpticalshopallSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PrescriptionComponent,
        PrescriptionDetailComponent,
        PrescriptionUpdateComponent,
        PrescriptionDeleteDialogComponent,
        PrescriptionDeletePopupComponent
    ],
    entryComponents: [
        PrescriptionComponent,
        PrescriptionUpdateComponent,
        PrescriptionDeleteDialogComponent,
        PrescriptionDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OpticalshopallPrescriptionModule {}
