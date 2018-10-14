import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISupplier } from 'app/shared/model/supplier.model';

type EntityResponseType = HttpResponse<ISupplier>;
type EntityArrayResponseType = HttpResponse<ISupplier[]>;

@Injectable({ providedIn: 'root' })
export class SupplierService {
    private resourceUrl = SERVER_API_URL + 'api/suppliers';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/suppliers';

    constructor(private http: HttpClient) {}

    create(supplier: ISupplier): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(supplier);
        return this.http
            .post<ISupplier>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(supplier: ISupplier): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(supplier);
        return this.http
            .put<ISupplier>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISupplier>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISupplier[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISupplier[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    private convertDateFromClient(supplier: ISupplier): ISupplier {
        const copy: ISupplier = Object.assign({}, supplier, {
            createdOn: supplier.createdOn != null && supplier.createdOn.isValid() ? supplier.createdOn.toJSON() : null,
            updatedOn: supplier.updatedOn != null && supplier.updatedOn.isValid() ? supplier.updatedOn.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.createdOn = res.body.createdOn != null ? moment(res.body.createdOn) : null;
        res.body.updatedOn = res.body.updatedOn != null ? moment(res.body.updatedOn) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((supplier: ISupplier) => {
            supplier.createdOn = supplier.createdOn != null ? moment(supplier.createdOn) : null;
            supplier.updatedOn = supplier.updatedOn != null ? moment(supplier.updatedOn) : null;
        });
        return res;
    }
}
