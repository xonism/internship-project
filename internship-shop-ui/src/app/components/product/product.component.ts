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

    constructor(private route: ActivatedRoute, private shopService: ShopService) {

    }

    ngOnInit() {
        this.subscriptions.push(this.route.params.subscribe(params => {
            this.id = params['id'];
        }));

        this.subscriptions.push(this.shopService.product$(this.id).subscribe(product => {
            this.product = product;
        }));
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
        const orderCreateRequest: OrderCreateRequest = {
            productId: +this.id,
            quantity: this.quantity,
            unitPrice: this.product?.price
        }

        this.subscriptions.push(this.shopService.order$(orderCreateRequest).subscribe(order => {
            this.order = order;
        }));
    }

    ngOnDestroy() {
        this.subscriptions.forEach(subscription => {
            subscription.unsubscribe();
        });
    }
}
