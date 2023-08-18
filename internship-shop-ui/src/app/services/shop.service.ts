import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Product} from '../interfaces/product';
import {Observable, throwError} from 'rxjs';
import {catchError, tap} from 'rxjs/operators';
import {environment} from 'src/environments/environment';
import {OrderCreateRequest} from '../interfaces/order-create-request';
import {Order} from '../interfaces/order';

@Injectable({providedIn: 'root'})
export class ShopService {
	private readonly productsUrl: string = `${environment.shopProxyUrl}/products`;
	private readonly ordersUrl: string = `${environment.shopProxyUrl}/orders`;

	constructor(private http: HttpClient) {

	};

	getProduct$(id: string) {
		return <Observable<Product>>this.http.get<Product>(`${this.productsUrl}/${id}`)
			.pipe(
				tap(console.debug),
				catchError(this.handleError));
	}

	getProducts$() {
		return <Observable<Product[]>>this.http.get<Product[]>(this.productsUrl)
			.pipe(
				tap(console.debug),
				catchError(this.handleError));
	}

	createOrder$(orderCreateRequest: OrderCreateRequest) {
		return <Observable<Order>>this.http.post<Order>(this.ordersUrl, orderCreateRequest)
			.pipe(
				tap(console.debug),
				catchError(this.handleError));
	}

	private handleError(error: HttpErrorResponse): Observable<never> {
		console.error(error);
		return throwError(() => new Error(`An error occurred - Error code: ${error.status}`));
	};
}
