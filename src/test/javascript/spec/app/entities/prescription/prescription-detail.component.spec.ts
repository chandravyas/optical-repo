/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OpticalshopallTestModule } from '../../../test.module';
import { PrescriptionDetailComponent } from 'app/entities/prescription/prescription-detail.component';
import { Prescription } from 'app/shared/model/prescription.model';

describe('Component Tests', () => {
    describe('Prescription Management Detail Component', () => {
        let comp: PrescriptionDetailComponent;
        let fixture: ComponentFixture<PrescriptionDetailComponent>;
        const route = ({ data: of({ prescription: new Prescription(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OpticalshopallTestModule],
                declarations: [PrescriptionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PrescriptionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PrescriptionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.prescription).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
