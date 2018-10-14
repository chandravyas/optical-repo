/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OpticalshopallTestModule } from '../../../test.module';
import { QualityDeleteDialogComponent } from 'app/entities/quality/quality-delete-dialog.component';
import { QualityService } from 'app/entities/quality/quality.service';

describe('Component Tests', () => {
    describe('Quality Management Delete Component', () => {
        let comp: QualityDeleteDialogComponent;
        let fixture: ComponentFixture<QualityDeleteDialogComponent>;
        let service: QualityService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OpticalshopallTestModule],
                declarations: [QualityDeleteDialogComponent]
            })
                .overrideTemplate(QualityDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(QualityDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QualityService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
