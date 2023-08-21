import {Component, OnDestroy, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {Product} from "src/app/interfaces/product";
import {Subscription} from 'rxjs';
import {ShopService} from "src/app/services/shop.service";
import {OrderCreateRequest} from "src/app/interfaces/order-create-request";
import {Order} from "src/app/interfaces/order";
import {SnackBarService} from "../../services/snack-bar.service";

@Component({
	selector: 'insh-product',
	templateUrl: './product.component.html',
	styleUrls: ['./product.component.scss']
})
export class ProductComponent implements OnInit, OnDestroy {
	private subscriptions: Subscription[] = [];

	id: string = '';
	product: Product | null = null;
	quantity: number = 1;
	order: Order | null = null;

	isLoading: boolean = true;

	constructor(
		private route: ActivatedRoute,
		private shopService: ShopService,
		private snackBarService: SnackBarService
	) {

	}

	ngOnInit() {
		this.subscriptions.push(
			this.route.params.subscribe(params => {
				this.id = params['id'];
			})
		);

		this.getProduct();
	}

	submitOrder() {
		this.isLoading = true;

		const orderCreateRequest: OrderCreateRequest = {
			productId: +this.id,
			quantity: this.quantity,
			unitPrice: this.product?.price
		}

		this.subscriptions.push(
			this.shopService.createOrder$(orderCreateRequest).subscribe({
				next: (order) => {
					this.order = order;
					this.getProduct();
					this.snackBarService.displaySnackBar("✅ Order successful");
				},
				error: () => this.snackBarService.displaySnackBar("❌ Error occurred")
			})
		);
	}

	getProduct() {
		this.subscriptions.push(
			this.shopService.getProduct$(this.id).subscribe(product => {
				this.product = product;
				this.isLoading = false;
			})
		);
	}

	onQuantityChanged(quantity: number) {
		this.quantity = quantity;
	}

	ngOnDestroy() {
		this.subscriptions.forEach(subscription => {
			subscription.unsubscribe();
		});
	}
}
