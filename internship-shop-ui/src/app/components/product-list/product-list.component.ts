import {Component, OnDestroy, OnInit} from "@angular/core";
import {Subscription} from 'rxjs';
import {ShopService} from "src/app/services/shop.service";
import {IProduct} from "../../interfaces/product";
import {SortType} from "../../enums/sort-type";
import {IFilterDialogData} from "../../interfaces/filter-dialog-data";
import {SortComparison} from "../product-sort/sort-comparison";

@Component({
	selector: 'app-product-list',
	templateUrl: './product-list.component.html',
	styleUrls: ['./product-list.component.scss']
})
export class ProductListComponent implements OnInit, OnDestroy {

	private subscription: Subscription = Subscription.EMPTY;

	selectedSort: SortType = SortType.NAME_ASCENDING;
	sortingOptions: string[] = Object.values(SortType);

	products!: IProduct[];
	filteredProducts!: IProduct[];

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
				this.setProductsAndFilteredProducts(products);
				this.setFilterRange(products);
			});
	}

	setProductsAndFilteredProducts(products: IProduct[]): void {
		const processedProducts: IProduct[] =
			this.sortProducts(products.filter((product: IProduct): boolean => product.quantity > 0));
		this.products = processedProducts;
		this.filteredProducts = processedProducts;
	}

	setFilterRange(products: IProduct[]): void {
		const sortedByPriceProducts: IProduct[] =
			[...products].sort((first: IProduct, second: IProduct) => first.price - second.price);
		this.min = Math.floor(sortedByPriceProducts[0].price);
		this.max = Math.ceil(sortedByPriceProducts[sortedByPriceProducts.length - 1].price);
	}

	sortProducts(products: IProduct[]): IProduct[] {
		return products.sort((firstProduct: IProduct, secondProduct: IProduct): number => {
			return SortComparison[this.selectedSort].getComparison(firstProduct, secondProduct);
		})
	}

	setFilterValues(filterValues: IFilterDialogData): void {
		if (filterValues.minPrice === 0 && filterValues.maxPrice === 0) {
			this.filteredProducts = this.sortProducts(this.products);
			return;
		}

		const filteredProducts: IProduct[] = this.products.filter(
			(product: IProduct) => product.price >= filterValues.minPrice && product.price <= filterValues.maxPrice);
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
