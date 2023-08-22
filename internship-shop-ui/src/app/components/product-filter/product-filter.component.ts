import {Component, EventEmitter, Input, OnDestroy, Output} from "@angular/core";
import {DialogPriceData} from "../../interfaces/dialog-price-data";
import {FilterDialogComponent} from "../filter-dialog/filter-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {Subscription} from "rxjs";
import {Product} from "../../interfaces/product";

@Component({
	selector: 'app-product-filter',
	templateUrl: './product-filter.component.html',
	styleUrls: ['./product-filter.component.scss']
})
export class ProductFilterComponent implements OnDestroy {

	private subscriptions: Subscription[] = [];

	@Input()
	min!: number;

	@Input()
	max!: number;

	@Input()
	products!: Product[];

	@Output()
	filteredProductsChange: EventEmitter<Product[]> = new EventEmitter<Product[]>();

	filteredProducts!: Product[];
	isFilterApplied: boolean = false;

	constructor(public dialog: MatDialog) {

	}

	openFilterDialog(): void {
		const dialogData: DialogPriceData = {
			min: this.min,
			max: this.max
		}

		const dialogRef = this.dialog.open(
			FilterDialogComponent,
			{data: dialogData}
		);

		this.subscriptions.push(
			dialogRef.afterClosed()
				.subscribe(result => {
					if (!result) return;

					this.filteredProducts =
						this.products.filter((product) => product.price >= result[0] && product.price <= result[1]);
					this.isFilterApplied = true;
					this.filteredProductsChange.emit(this.filteredProducts);
				})
		);
	}

	removeFilters(): void {
		this.filteredProducts = this.products;
		this.isFilterApplied = false;
		this.filteredProductsChange.emit(this.filteredProducts);
	}

	ngOnDestroy(): void {
		this.subscriptions.forEach((subscription: Subscription) => {
			subscription.unsubscribe();
		});
	}
}
