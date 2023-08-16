import { Component, OnDestroy, OnInit } from "@angular/core";
import { Product } from "src/app/interfaces/product";
import { Subscription } from 'rxjs';
import { ShopService } from "src/app/services/shop.service";

@Component({
    selector: 'insh-product-list',
    templateUrl: './product-list.component.html',
    styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit, OnDestroy {
    private subscription: Subscription = Subscription.EMPTY;

    products: Product[] | null = null;

    constructor(private shopService: ShopService) {

    }

    ngOnInit(): void {
        this.subscription = this.shopService.products$().subscribe(products => {
            this.products = products;
        });
    }

    ngOnDestroy(): void {
        this.subscription.unsubscribe();
    }
}
