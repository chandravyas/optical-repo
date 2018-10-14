import { NgModule } from '@angular/core';

import { OpticalshopallSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [OpticalshopallSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [OpticalshopallSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class OpticalshopallSharedCommonModule {}
