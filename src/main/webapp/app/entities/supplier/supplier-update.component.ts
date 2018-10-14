import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ISupplier } from 'app/shared/model/supplier.model';
import { SupplierService } from './supplier.service';

@Component({
    selector: 'jhi-supplier-update',
    templateUrl: './supplier-update.component.html'
})
export class SupplierUpdateComponent implements OnInit {
    supplier: ISupplier;
    isSaving: boolean;
    createdOn: string;
    updatedOn: string;

    constructor(private supplierService: SupplierService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ supplier }) => {
            this.supplier = supplier;
            this.createdOn = this.supplier.createdOn != null ? this.supplier.createdOn.format(DATE_TIME_FORMAT) : null;
            this.updatedOn = this.supplier.updatedOn != null ? this.supplier.updatedOn.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.supplier.createdOn = this.createdOn != null ? moment(this.createdOn, DATE_TIME_FORMAT) : null;
        this.supplier.updatedOn = this.updatedOn != null ? moment(this.updatedOn, DATE_TIME_FORMAT) : null;
        if (this.supplier.id !== undefined) {
            this.subscribeToSaveResponse(this.supplierService.update(this.supplier));
        } else {
            this.subscribeToSaveResponse(this.supplierService.create(this.supplier));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISupplier>>) {
        result.subscribe((res: HttpResponse<ISupplier>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
