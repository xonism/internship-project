import {Component, OnDestroy, OnInit} from "@angular/core";
import {Subscription} from 'rxjs';
import {ShopService} from "src/app/services/shop.service";
import {IProduct} from "../../interfaces/product";
import {IFilterDialogData} from "../../interfaces/filter-dialog-data";
import {ProductSortingTypes} from "./ProductSortingTypes";
import {ISortTypeInfo} from "../../interfaces/sort-type-info";

@Component({
	selector: 'app-product-list',
	templateUrl: './product-list.component.html',
	styleUrls: ['./product-list.component.scss']
})
export class ProductListComponent implements OnInit, OnDestroy {

	private subscription: Subscription = Subscription.EMPTY;

	selectedSort: ISortTypeInfo = ProductSortingTypes.NAME_ASCENDING;
	sortingOptions: ISortTypeInfo[] = Object.values(ProductSortingTypes);

	products!: IProduct[];
	processedProducts!: IProduct[];

	min!: number;
	max!: number;

	constructor(private shopService: ShopService) {

	}

	ngOnInit(): void {
		this.loadProductList();
	}

	loadProductList(): void {
		this.subscription = this.shopService.getProducts$()
			.subscribe((products: IProduct[]): void => {
				this.setProductsAndProcessedProducts(products);
				this.setFilterRange(products);
			});
	}

	setProductsAndProcessedProducts(products: IProduct[]): void {
		const processedProducts: IProduct[] = products.filter((product: IProduct): boolean => product.quantity > 0);
		this.products = processedProducts;
		this.processedProducts = processedProducts;
	}

	setFilterRange(products: IProduct[]): void {
		const sortedByPriceProducts: IProduct[] =
			[...products].sort((first: IProduct, second: IProduct) => first.price - second.price);
		this.min = Math.floor(sortedByPriceProducts[0].price);
		this.max = Math.ceil(sortedByPriceProducts[sortedByPriceProducts.length - 1].price);
	}

	setFilterValues(filterValues: IFilterDialogData): void {
		if (filterValues.minPrice === 0 && filterValues.maxPrice === 0) {
			this.processedProducts = this.products;
			return;
		}

		this.processedProducts = this.products.filter(
			(product: IProduct) => product.price >= filterValues.minPrice && product.price <= filterValues.maxPrice);
	}

	setSelectedSort(selectedSort: ISortTypeInfo): void {
		this.selectedSort = selectedSort;
	}

	setProcessedProducts(sortedProducts: IProduct[]): void {
		this.processedProducts = sortedProducts;
	}

	ngOnDestroy(): void {
		this.subscription.unsubscribe();
	}
}
