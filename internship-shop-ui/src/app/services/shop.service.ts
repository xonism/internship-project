import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Product} from '../interfaces/product';
import {Observable} from 'rxjs';
import {tap} from 'rxjs/operators';
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
			.pipe(tap(console.debug));
	}

	getProducts$() {
		return <Observable<Product[]>>this.http.get<Product[]>(this.productsUrl)
			.pipe(tap(console.debug));
	}

	createOrder$(orderCreateRequest: OrderCreateRequest) {
		return <Observable<Order>>this.http.post<Order>(this.ordersUrl, orderCreateRequest)
			.pipe(tap(console.debug));
	}
}
