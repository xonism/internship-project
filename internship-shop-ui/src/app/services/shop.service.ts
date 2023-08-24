import {HttpClient, HttpResponse} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {IProduct} from '../interfaces/product';
import {Observable} from 'rxjs';
import {environment} from 'src/environments/environment';
import {IOrderCreateRequest} from '../interfaces/order-create-request';
import {IOrder} from '../interfaces/order';

@Injectable({providedIn: 'root'})
export class ShopService {

	private readonly productsUrl: string = `${environment.shopUrl}/products`;
	private readonly ordersUrl: string = `${environment.shopUrl}/orders`;
	private readonly reportsUrl: string = `${this.ordersUrl}/reports`;

	constructor(private http: HttpClient) {

	};

	getReportData$(dateTime: string): Observable<string> {
		return <Observable<string>>this.http.get(
			`${this.reportsUrl}/${dateTime}`,
			{responseType: 'text'}
		);
	}

	getReportDataBlob$(dateTime: string): Observable<HttpResponse<Blob>> {
		return <Observable<HttpResponse<Blob>>>this.http.get(
			`${this.reportsUrl}/${dateTime}`,
			{responseType: 'blob', observe: 'response'}
		);
	}

	getProduct$(id: string): Observable<IProduct> {
		return <Observable<IProduct>>this.http.get<IProduct>(`${this.productsUrl}/${id}`);
	}

	getProducts$(): Observable<IProduct[]> {
		return <Observable<IProduct[]>>this.http.get<IProduct[]>(this.productsUrl);
	}

	createOrder$(orderCreateRequest: IOrderCreateRequest): Observable<IOrder> {
		return <Observable<IOrder>>this.http.post<IOrder>(this.ordersUrl, orderCreateRequest);
	}
}
