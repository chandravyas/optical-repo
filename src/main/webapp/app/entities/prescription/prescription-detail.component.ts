import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPrescription } from 'app/shared/model/prescription.model';

@Component({
    selector: 'jhi-prescription-detail',
    templateUrl: './prescription-detail.component.html'
})
export class PrescriptionDetailComponent implements OnInit {
    prescription: IPrescription;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
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
    previousState() {
        window.history.back();
    }
}
