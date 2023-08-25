import {Component, OnDestroy, OnInit} from "@angular/core";
import {Subscription} from 'rxjs';
import {ShopService} from "src/app/services/shop.service";
import {IProduct} from "../../interfaces/product";
import {ProductSortingTypes} from "./product-sorting-types";
import {ISortTypeInfo} from "../../interfaces/sort-type-info";
import {IFilterDialogData} from "../../interfaces/filter-dialog-data";

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

	filterDialogData!: IFilterDialogData;

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
		this.filterDialogData = {
			minRange: Math.floor(sortedByPriceProducts[0].price),
			maxRange: Math.ceil(sortedByPriceProducts[sortedByPriceProducts.length - 1].price)
		}
	}

	setSelectedSort(selectedSort: ISortTypeInfo): void {
		this.selectedSort = selectedSort;
	}

	setProcessedProducts(products: IProduct[]): void {
		this.processedProducts = products;
	}

	ngOnDestroy(): void {
		this.subscription.unsubscribe();
	}
}
