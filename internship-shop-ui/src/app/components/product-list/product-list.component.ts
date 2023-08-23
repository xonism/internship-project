import {Component, OnDestroy, OnInit} from "@angular/core";
import {Subscription} from 'rxjs';
import {ShopService} from "src/app/services/shop.service";
import {Product} from "../../interfaces/product";
import {SortType} from "../../enums/sort-by.enum";
import {FilterDialogData} from "../../interfaces/filter-dialog-data";
import {SortingInfo} from "../../enums/sorting-Info";

@Component({
	selector: 'app-product-list',
	templateUrl: './product-list.component.html',
	styleUrls: ['./product-list.component.scss']
})
export class ProductListComponent implements OnInit, OnDestroy {

	private subscription: Subscription = Subscription.EMPTY;

	selectedSort: SortType = SortType.NAME_ASCENDING;
	sortingOptions: string[] = Object.values(SortType);

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
			return SortingInfo[this.selectedSort].sort(first, second);
		})
	}

	setFilterValues(filterValues: FilterDialogData): void {
		if (filterValues.minPrice === 0 && filterValues.maxPrice === 0) {
			this.filteredProducts = this.sortProducts(this.products);
			return;
		}

		const filteredProducts: Product[] = this.products.filter(
			(product: Product) => product.price >= filterValues.minPrice && product.price <= filterValues.maxPrice);
		this.filteredProducts = this.sortProducts(filteredProducts);
	}

	setSelectedSort(selectedSort: SortType): void {
		this.selectedSort = selectedSort;
		this.filteredProducts = this.sortProducts(this.filteredProducts);
	}

	ngOnDestroy(): void {
		this.subscription.unsubscribe();
	}
}
