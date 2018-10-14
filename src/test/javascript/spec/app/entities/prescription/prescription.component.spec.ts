/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OpticalshopallTestModule } from '../../../test.module';
import { PrescriptionComponent } from 'app/entities/prescription/prescription.component';
import { PrescriptionService } from 'app/entities/prescription/prescription.service';
import { Prescription } from 'app/shared/model/prescription.model';

describe('Component Tests', () => {
    describe('Prescription Management Component', () => {
        let comp: PrescriptionComponent;
        let fixture: ComponentFixture<PrescriptionComponent>;
        let service: PrescriptionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OpticalshopallTestModule],
                declarations: [PrescriptionComponent],
                providers: []
            })
                .overrideTemplate(PrescriptionComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PrescriptionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrescriptionService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Prescription(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.prescriptions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
