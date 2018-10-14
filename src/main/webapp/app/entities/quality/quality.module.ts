import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OpticalshopallSharedModule } from 'app/shared';
import {
    QualityComponent,
    QualityDetailComponent,
    QualityUpdateComponent,
    QualityDeletePopupComponent,
    QualityDeleteDialogComponent,
    qualityRoute,
    qualityPopupRoute
} from './';

const ENTITY_STATES = [...qualityRoute, ...qualityPopupRoute];

@NgModule({
    imports: [OpticalshopallSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        QualityComponent,
        QualityDetailComponent,
        QualityUpdateComponent,
        QualityDeleteDialogComponent,
        QualityDeletePopupComponent
    ],
    entryComponents: [QualityComponent, QualityUpdateComponent, QualityDeleteDialogComponent, QualityDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OpticalshopallQualityModule {}
