import { Component, OnDestroy, OnInit } from "@angular/core";
import { Product } from "src/app/interfaces/product";
import { Subscription, filter, map } from 'rxjs';
import { ShopService } from "src/app/services/shop.service";

@Component({
    selector: 'insh-product-list',
    templateUrl: './product-list.component.html',
    styleUrls: ['./product-list.component.css']
})
export class ProductListComponent {
    
    constructor(private shopService: ShopService) {

    }

    availableProducts$ = this.shopService.getProducts$().pipe(
        map((products) => products.filter(product => product.quantity > 0))
    );
}
