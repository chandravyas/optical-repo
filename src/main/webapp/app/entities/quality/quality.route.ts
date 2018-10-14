import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Quality } from 'app/shared/model/quality.model';
import { QualityService } from './quality.service';
import { QualityComponent } from './quality.component';
import { QualityDetailComponent } from './quality-detail.component';
import { QualityUpdateComponent } from './quality-update.component';
import { QualityDeletePopupComponent } from './quality-delete-dialog.component';
import { IQuality } from 'app/shared/model/quality.model';

@Injectable({ providedIn: 'root' })
export class QualityResolve implements Resolve<IQuality> {
    constructor(private service: QualityService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((quality: HttpResponse<Quality>) => quality.body));
        }
        return of(new Quality());
    }
}

export const qualityRoute: Routes = [
    {
        path: 'quality',
        component: QualityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Qualities'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'quality/:id/view',
        component: QualityDetailComponent,
        resolve: {
            quality: QualityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Qualities'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'quality/new',
        component: QualityUpdateComponent,
        resolve: {
            quality: QualityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Qualities'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'quality/:id/edit',
        component: QualityUpdateComponent,
        resolve: {
            quality: QualityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Qualities'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const qualityPopupRoute: Routes = [
    {
        path: 'quality/:id/delete',
        component: QualityDeletePopupComponent,
        resolve: {
            quality: QualityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Qualities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
