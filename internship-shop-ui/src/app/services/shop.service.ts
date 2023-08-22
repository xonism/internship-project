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

	private readonly productsUrl: string = `${environment.shopUrl}/products`;
	private readonly ordersUrl: string = `${environment.shopUrl}/orders`;

	constructor(private http: HttpClient) {

	};

	getProduct$(id: string) {
		return <Observable<Product>>this.http.get<Product>(`${this.productsUrl}/${id}`);
	}

	getProducts$() {
		return <Observable<Product[]>>this.http.get<Product[]>(this.productsUrl);
	}

	createOrder$(orderCreateRequest: OrderCreateRequest) {
		return <Observable<Order>>this.http.post<Order>(this.ordersUrl, orderCreateRequest);
	}
}
