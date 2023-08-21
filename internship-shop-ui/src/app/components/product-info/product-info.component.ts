import {Component, Input} from "@angular/core";
import {Product} from "../../interfaces/product";

@Component({
	selector: 'insh-product-info',
	templateUrl: './product-info.component.html',
	styleUrls: ['./product-info.component.scss']
})
export class ProductInfoComponent {

	@Input()
	product: Product | null = null;

	renderPrice() {
		return !this.product
			? 'Loading..'
			: `${this.product.price} €`;
	}
}
