/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { PrescriptionService } from 'app/entities/prescription/prescription.service';
import { IPrescription, Prescription } from 'app/shared/model/prescription.model';

describe('Service Tests', () => {
    describe('Prescription Service', () => {
        let injector: TestBed;
        let service: PrescriptionService;
        let httpMock: HttpTestingController;
        let elemDefault: IPrescription;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(PrescriptionService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new Prescription(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', false);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign({}, elemDefault);
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Prescription', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new Prescription(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Prescription', async () => {
                const returnedFromService = Object.assign(
                    {
                        phonenumber: 'BBBBBB',
                        patientName: 'BBBBBB',
                        doctorName: 'BBBBBB',
                        rightVision: 'BBBBBB',
                        leftVision: 'BBBBBB',
                        powerDetails: 'BBBBBB',
                        continousWear: true
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Prescription', async () => {
                const returnedFromService = Object.assign(
                    {
                        phonenumber: 'BBBBBB',
                        patientName: 'BBBBBB',
                        doctorName: 'BBBBBB',
                        rightVision: 'BBBBBB',
                        leftVision: 'BBBBBB',
                        powerDetails: 'BBBBBB',
                        continousWear: true
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .query(expected)
                    .pipe(take(1), map(resp => resp.body))
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Prescription', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
