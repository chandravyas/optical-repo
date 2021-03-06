/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ProductOrderService } from 'app/entities/product-order/product-order.service';
import { IProductOrder, ProductOrder, OrderStatus } from 'app/shared/model/product-order.model';

describe('Service Tests', () => {
    describe('ProductOrder Service', () => {
        let injector: TestBed;
        let service: ProductOrderService;
        let httpMock: HttpTestingController;
        let elemDefault: IProductOrder;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(ProductOrderService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new ProductOrder(0, 0, 0, OrderStatus.COMPLETED, currentDate, 0);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        placedDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a ProductOrder', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        placedDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        placedDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new ProductOrder(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a ProductOrder', async () => {
                const returnedFromService = Object.assign(
                    {
                        quantity: 1,
                        totalCost: 1,
                        status: 'BBBBBB',
                        placedDate: currentDate.format(DATE_TIME_FORMAT),
                        invoiceId: 1
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        placedDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of ProductOrder', async () => {
                const returnedFromService = Object.assign(
                    {
                        quantity: 1,
                        totalCost: 1,
                        status: 'BBBBBB',
                        placedDate: currentDate.format(DATE_TIME_FORMAT),
                        invoiceId: 1
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        placedDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(take(1), map(resp => resp.body))
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a ProductOrder', async () => {
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
