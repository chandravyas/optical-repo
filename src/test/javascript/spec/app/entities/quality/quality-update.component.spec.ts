/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { OpticalshopallTestModule } from '../../../test.module';
import { QualityUpdateComponent } from 'app/entities/quality/quality-update.component';
import { QualityService } from 'app/entities/quality/quality.service';
import { Quality } from 'app/shared/model/quality.model';

describe('Component Tests', () => {
    describe('Quality Management Update Component', () => {
        let comp: QualityUpdateComponent;
        let fixture: ComponentFixture<QualityUpdateComponent>;
        let service: QualityService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OpticalshopallTestModule],
                declarations: [QualityUpdateComponent]
            })
                .overrideTemplate(QualityUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(QualityUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QualityService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Quality(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.quality = entity;
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
                    const entity = new Quality();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.quality = entity;
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
