import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IQuality } from 'app/shared/model/quality.model';

type EntityResponseType = HttpResponse<IQuality>;
type EntityArrayResponseType = HttpResponse<IQuality[]>;

@Injectable({ providedIn: 'root' })
export class QualityService {
    private resourceUrl = SERVER_API_URL + 'api/qualities';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/qualities';

    constructor(private http: HttpClient) {}

    create(quality: IQuality): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(quality);
        return this.http
            .post<IQuality>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(quality: IQuality): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(quality);
        return this.http
            .put<IQuality>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IQuality>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IQuality[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IQuality[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    private convertDateFromClient(quality: IQuality): IQuality {
        const copy: IQuality = Object.assign({}, quality, {
            createdOn: quality.createdOn != null && quality.createdOn.isValid() ? quality.createdOn.toJSON() : null,
            updatedOn: quality.updatedOn != null && quality.updatedOn.isValid() ? quality.updatedOn.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.createdOn = res.body.createdOn != null ? moment(res.body.createdOn) : null;
        res.body.updatedOn = res.body.updatedOn != null ? moment(res.body.updatedOn) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((quality: IQuality) => {
            quality.createdOn = quality.createdOn != null ? moment(quality.createdOn) : null;
            quality.updatedOn = quality.updatedOn != null ? moment(quality.updatedOn) : null;
        });
        return res;
    }
}
