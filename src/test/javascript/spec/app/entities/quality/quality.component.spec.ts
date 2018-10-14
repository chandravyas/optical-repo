/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OpticalshopallTestModule } from '../../../test.module';
import { QualityComponent } from 'app/entities/quality/quality.component';
import { QualityService } from 'app/entities/quality/quality.service';
import { Quality } from 'app/shared/model/quality.model';

describe('Component Tests', () => {
    describe('Quality Management Component', () => {
        let comp: QualityComponent;
        let fixture: ComponentFixture<QualityComponent>;
        let service: QualityService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OpticalshopallTestModule],
                declarations: [QualityComponent],
                providers: []
            })
                .overrideTemplate(QualityComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(QualityComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QualityService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Quality(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.qualities[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
