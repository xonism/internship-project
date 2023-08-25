import {Component, OnDestroy, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {IProduct} from "src/app/interfaces/product";
import {Subscription} from 'rxjs';
import {ShopService} from "src/app/services/shop.service";
import {IOrderCreateRequest} from "src/app/interfaces/order-create-request";
import {SnackBarService} from "../../services/snack-bar.service";
import {HttpErrorResponse} from "@angular/common/http";
import {IErrorMessage} from "../../interfaces/error-message";

@Component({
	selector: 'app-product',
	templateUrl: './product.component.html',
	styleUrls: ['./product.component.scss']
})
export class ProductComponent implements OnInit, OnDestroy {
	private subscriptions: Subscription[] = [];

	id!: string;
	product?: IProduct;
	quantity: number = 1;
	isLoading: boolean = true;

	constructor(private route: ActivatedRoute,
							private shopService: ShopService,
							private snackBarService: SnackBarService) {

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

		const orderCreateRequest: IOrderCreateRequest = {
			productId: +this.id,
			quantity: this.quantity,
			unitPrice: this.product?.price
		}

		this.subscriptions.push(
			this.shopService.createOrder$(orderCreateRequest).subscribe({
				next: () => {
					this.getProduct();
					this.snackBarService.displaySnackBar($localize`✅ Order successful`);
				},
				error: (error: HttpErrorResponse) => {
					const errorMessage: IErrorMessage = error.error;
					this.snackBarService.displaySnackBar(`❌ ${errorMessage.error}`)
				}
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

	setQuantity(quantity: number) {
		this.quantity = quantity;
	}

	ngOnDestroy() {
		this.subscriptions.forEach(subscription => {
			subscription.unsubscribe();
		});
	}
}
