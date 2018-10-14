import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPrescription } from 'app/shared/model/prescription.model';

type EntityResponseType = HttpResponse<IPrescription>;
type EntityArrayResponseType = HttpResponse<IPrescription[]>;

@Injectable({ providedIn: 'root' })
export class PrescriptionService {
    private resourceUrl = SERVER_API_URL + 'api/prescriptions';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/prescriptions';

    constructor(private http: HttpClient) {}

    create(prescription: IPrescription): Observable<EntityResponseType> {
        return this.http.post<IPrescription>(this.resourceUrl, prescription, { observe: 'response' });
    }

    update(prescription: IPrescription): Observable<EntityResponseType> {
        return this.http.put<IPrescription>(this.resourceUrl, prescription, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPrescription>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPrescription[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPrescription[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
