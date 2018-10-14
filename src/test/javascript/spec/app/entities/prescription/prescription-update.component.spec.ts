/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { OpticalshopallTestModule } from '../../../test.module';
import { PrescriptionUpdateComponent } from 'app/entities/prescription/prescription-update.component';
import { PrescriptionService } from 'app/entities/prescription/prescription.service';
import { Prescription } from 'app/shared/model/prescription.model';

describe('Component Tests', () => {
    describe('Prescription Management Update Component', () => {
        let comp: PrescriptionUpdateComponent;
        let fixture: ComponentFixture<PrescriptionUpdateComponent>;
        let service: PrescriptionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OpticalshopallTestModule],
                declarations: [PrescriptionUpdateComponent]
            })
                .overrideTemplate(PrescriptionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PrescriptionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrescriptionService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Prescription(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.prescription = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Prescription();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.prescription = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
