import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { IPrescription } from 'app/shared/model/prescription.model';
import { PrescriptionService } from './prescription.service';

@Component({
    selector: 'jhi-prescription-update',
    templateUrl: './prescription-update.component.html'
})
export class PrescriptionUpdateComponent implements OnInit {
    prescription: IPrescription;
    isSaving: boolean;

    constructor(
        private dataUtils: JhiDataUtils,
        private prescriptionService: PrescriptionService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ prescription }) => {
            this.prescription = prescription;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.prescription.id !== undefined) {
            this.subscribeToSaveResponse(this.prescriptionService.update(this.prescription));
        } else {
            this.subscribeToSaveResponse(this.prescriptionService.create(this.prescription));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPrescription>>) {
        result.subscribe((res: HttpResponse<IPrescription>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
