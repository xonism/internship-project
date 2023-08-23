import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {IProduct} from '../interfaces/product';
import {Observable} from 'rxjs';
import {tap} from 'rxjs/operators';
import {environment} from 'src/environments/environment';
import {IOrderCreateRequest} from '../interfaces/order-create-request';
import {IOrder} from '../interfaces/order';

@Injectable({providedIn: 'root'})
export class ShopService {

	private readonly productsUrl: string = `${environment.shopUrl}/products`;
	private readonly ordersUrl: string = `${environment.shopUrl}/orders`;

	constructor(private http: HttpClient) {

	};

	getProduct$(id: string) {
		return <Observable<IProduct>>this.http.get<IProduct>(`${this.productsUrl}/${id}`);
	}

	getProducts$() {
		return <Observable<IProduct[]>>this.http.get<IProduct[]>(this.productsUrl);
	}

	createOrder$(orderCreateRequest: IOrderCreateRequest) {
		return <Observable<IOrder>>this.http.post<IOrder>(this.ordersUrl, orderCreateRequest);
	}
}
