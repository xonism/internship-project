import {Component, OnDestroy, OnInit} from "@angular/core";
import {Subscription} from 'rxjs';
import {ShopService} from "src/app/services/shop.service";
import {Product} from "../../interfaces/product";
import {SortingOptions} from "../../enums/sorting-options.enum";
import {DialogPriceData} from "../../interfaces/dialog-price-data";

@Component({
	selector: 'app-product-list',
	templateUrl: './product-list.component.html',
	styleUrls: ['./product-list.component.scss']
})
export class ProductListComponent implements OnInit, OnDestroy {

	private subscription: Subscription = Subscription.EMPTY;

	selectedSort: string = SortingOptions.NAME_ASCENDING;

	products!: Product[];
	filteredProducts!: Product[];

	min!: number;
	max!: number;

	constructor(private shopService: ShopService) {

	}

	ngOnInit(): void {
		this.loadProductList();
	}

	loadProductList(): void {
		this.subscription = this.shopService.getProducts$()
			.subscribe((products: Product[]): void => {
				this.setProductsAndFilteredProducts(products);
				this.setFilterRange(products);
			});
	}

	setProductsAndFilteredProducts(products: Product[]): void {
		const processedProducts: Product[] =
			this.sortProducts(products.filter((product: Product) => product.quantity > 0));
		this.products = processedProducts;
		this.filteredProducts = processedProducts;
	}

	setFilterRange(products: Product[]): void {
		const sortedByPriceProducts: Product[] =
			[...products].sort((first: Product, second: Product) => first.price - second.price);
		this.min = Math.floor(sortedByPriceProducts[0].price);
		this.max = Math.ceil(sortedByPriceProducts[sortedByPriceProducts.length - 1].price);
	}

	sortProducts(products: Product[]): Product[] {
		return products.sort((first: Product, second: Product): number => {
			// TODO: convert to object
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

	setFilterValues(filterValues: DialogPriceData): void {
		if (filterValues.min === 0 && filterValues.max === 0) {
			this.filteredProducts = this.sortProducts(this.products);
			return;
		}

		const filteredProducts: Product[] = this.products.filter(
			(product: Product) => product.price >= filterValues.min && product.price <= filterValues.max);
		this.filteredProducts = this.sortProducts(filteredProducts);
	}

	setSelectedSort(selectedSort: string): void {
		this.selectedSort = selectedSort;
		this.filteredProducts = this.sortProducts(this.filteredProducts);
	}

	ngOnDestroy(): void {
		this.subscription.unsubscribe();
	}
}
