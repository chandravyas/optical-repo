import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IQuality } from 'app/shared/model/quality.model';
import { QualityService } from './quality.service';

@Component({
    selector: 'jhi-quality-update',
    templateUrl: './quality-update.component.html'
})
export class QualityUpdateComponent implements OnInit {
    quality: IQuality;
    isSaving: boolean;
    createdOn: string;
    updatedOn: string;

    constructor(private qualityService: QualityService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ quality }) => {
            this.quality = quality;
            this.createdOn = this.quality.createdOn != null ? this.quality.createdOn.format(DATE_TIME_FORMAT) : null;
            this.updatedOn = this.quality.updatedOn != null ? this.quality.updatedOn.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.quality.createdOn = this.createdOn != null ? moment(this.createdOn, DATE_TIME_FORMAT) : null;
        this.quality.updatedOn = this.updatedOn != null ? moment(this.updatedOn, DATE_TIME_FORMAT) : null;
        if (this.quality.id !== undefined) {
            this.subscribeToSaveResponse(this.qualityService.update(this.quality));
        } else {
            this.subscribeToSaveResponse(this.qualityService.create(this.quality));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IQuality>>) {
        result.subscribe((res: HttpResponse<IQuality>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
