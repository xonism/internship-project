import { Component, OnDestroy, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { Product } from "src/app/interfaces/product";
import { Subscription } from 'rxjs';
import { ShopService } from "src/app/services/shop.service";
import { OrderCreateRequest } from "src/app/interfaces/order-create-request";
import { Order } from "src/app/interfaces/order";

@Component({
    selector: 'insh-product',
    templateUrl: './product.component.html',
    styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit, OnDestroy {
    private subscriptions: Subscription[] = [];

    id: string = '';
    product: Product | null = null;
    quantity: number = 1;
    order: Order | null = null;

    isLoading: boolean = true;

    constructor(private route: ActivatedRoute, private shopService: ShopService) {

    }

    ngOnInit() {
        this.subscriptions.push(this.route.params.subscribe(params => {
            this.id = params['id'];
        }));

        this.getProduct();
    }

    decrementQuantity() {
        if (this.quantity > 1) {
            this.quantity -= 1;
        }
    }

    incrementQuantity() {
        if (this.product && this.quantity < this.product.quantity) {
            this.quantity += 1;
        }
    }

    submitOrder() {
        this.isLoading = true;

        const orderCreateRequest: OrderCreateRequest = {
            productId: +this.id,
            quantity: this.quantity,
            unitPrice: this.product?.price
        }

        this.subscriptions.push(this.shopService.createOrder$(orderCreateRequest).subscribe(order => {
            this.order = order;
            this.getProduct();
        }));
    }

    getProduct() {
        this.subscriptions.push(this.shopService.getProduct$(this.id).subscribe(product => {
            this.product = product;
            this.isLoading = false;
        }));
    }

    ngOnDestroy() {
        this.subscriptions.forEach(subscription => {
            subscription.unsubscribe();
        });
    }
}
