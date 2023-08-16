import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Product } from '../interfaces/product';
import { Observable, throwError } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({ providedIn: 'root' })
export class ShopService {
    private readonly productsUrl: string = `${environment.shopProxyUrl}/products`;

    // options = {
    //     headers: new HttpHeaders({
    //         'Authorization': `Basic ${btoa(`${environment.username}:${environment.password}`)}`
    //     })
    // };

    constructor(private http: HttpClient) {

    };

    product$(id: string) {
        return <Observable<Product>>this.http.get<Product>(`${this.productsUrl}/${id}`)
            .pipe(
                tap(console.debug),
                catchError(this.handleError));
    }

    products$() {
        return <Observable<Product[]>>this.http.get<Product[]>(this.productsUrl)
            .pipe(
                tap(console.debug),
                catchError(this.handleError));
    }

    private handleError(error: HttpErrorResponse): Observable<never> {
        console.error(error);
        return throwError(() => new Error(`An error occurred - Error code: ${error.status}`));
    };
}
