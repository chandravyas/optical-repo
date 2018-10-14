/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OpticalshopallTestModule } from '../../../test.module';
import { QualityDetailComponent } from 'app/entities/quality/quality-detail.component';
import { Quality } from 'app/shared/model/quality.model';

describe('Component Tests', () => {
    describe('Quality Management Detail Component', () => {
        let comp: QualityDetailComponent;
        let fixture: ComponentFixture<QualityDetailComponent>;
        const route = ({ data: of({ quality: new Quality(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OpticalshopallTestModule],
                declarations: [QualityDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(QualityDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(QualityDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.quality).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
