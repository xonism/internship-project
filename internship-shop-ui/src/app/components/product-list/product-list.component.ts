import {Component, OnDestroy, OnInit} from "@angular/core";
import {Subscription} from 'rxjs';
import {ShopService} from "src/app/services/shop.service";
import {Product} from "../../interfaces/product";
import {SortingOptions} from "../../enums/sorting-options.enum";

@Component({
	selector: 'app-product-list',
	templateUrl: './product-list.component.html',
	styleUrls: ['./product-list.component.scss']
})
export class ProductListComponent implements OnInit, OnDestroy {

	private subscriptions: Subscription[] = [];

	selectedSort: string = SortingOptions.NAME_ASCENDING;

	products!: Product[];
	filteredProducts!: Product[];

	min!: number;
	max!: number;

	constructor(private shopService: ShopService) {

	}

	ngOnInit(): void {
		this.subscriptions.push(
			this.shopService.getProducts$()
				.subscribe(products => {
					const processedProducts: Product[] =
						this.sortProducts(products.filter(product => product.quantity > 0));
					this.products = processedProducts;
					this.filteredProducts = processedProducts;

					const sortedProducts =
						[...products].sort((first, second) => first.price - second.price);
					this.min = Math.floor(sortedProducts[0].price);
					this.max = Math.ceil(sortedProducts[sortedProducts.length - 1].price);
				})
		);
	}

	sortProducts(products: Product[]): Product[] {
		return products.sort((first, second) => {
			switch (this.selectedSort) {
				case SortingOptions.HIGHEST_PRICE:
					return second.price - first.price;
				case SortingOptions.LOWEST_PRICE:
					return first.price - second.price;
				case SortingOptions.NAME_DESCENDING:
					return second.name.localeCompare(first.name);
				case SortingOptions.NAME_ASCENDING:
					return first.name.localeCompare(second.name);
				default:
					this.selectedSort = SortingOptions.NAME_ASCENDING;
					return first.name.localeCompare(second.name);
			}
		})
	}

	setFilteredProducts(filteredProducts: Product[]) {
		this.filteredProducts = this.sortProducts(filteredProducts);
	}

	setSelectedSort(selectedSort: string) {
		this.selectedSort = selectedSort;
		this.filteredProducts = this.sortProducts(this.filteredProducts);
	}

	ngOnDestroy(): void {
		this.subscriptions.forEach((subscription: Subscription) => {
			subscription.unsubscribe();
		});
	}
}
