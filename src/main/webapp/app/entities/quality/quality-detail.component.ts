import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQuality } from 'app/shared/model/quality.model';

@Component({
    selector: 'jhi-quality-detail',
    templateUrl: './quality-detail.component.html'
})
export class QualityDetailComponent implements OnInit {
    quality: IQuality;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ quality }) => {
            this.quality = quality;
        });
    }

    previousState() {
        window.history.back();
    }
}
