import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Prescription } from 'app/shared/model/prescription.model';
import { PrescriptionService } from './prescription.service';
import { PrescriptionComponent } from './prescription.component';
import { PrescriptionDetailComponent } from './prescription-detail.component';
import { PrescriptionUpdateComponent } from './prescription-update.component';
import { PrescriptionDeletePopupComponent } from './prescription-delete-dialog.component';
import { IPrescription } from 'app/shared/model/prescription.model';

@Injectable({ providedIn: 'root' })
export class PrescriptionResolve implements Resolve<IPrescription> {
    constructor(private service: PrescriptionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((prescription: HttpResponse<Prescription>) => prescription.body));
        }
        return of(new Prescription());
    }
}

export const prescriptionRoute: Routes = [
    {
        path: 'prescription',
        component: PrescriptionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Prescriptions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'prescription/:id/view',
        component: PrescriptionDetailComponent,
        resolve: {
            prescription: PrescriptionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Prescriptions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'prescription/new',
        component: PrescriptionUpdateComponent,
        resolve: {
            prescription: PrescriptionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Prescriptions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'prescription/:id/edit',
        component: PrescriptionUpdateComponent,
        resolve: {
            prescription: PrescriptionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Prescriptions'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const prescriptionPopupRoute: Routes = [
    {
        path: 'prescription/:id/delete',
        component: PrescriptionDeletePopupComponent,
        resolve: {
            prescription: PrescriptionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Prescriptions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
