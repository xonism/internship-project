import {Component, Input} from "@angular/core";
import {Product} from "../../interfaces/product";

@Component({
	selector: 'insh-product-info',
	templateUrl: './product-info.component.html',
	styleUrls: ['./product-info.component.scss']
})
export class ProductInfoComponent {

	@Input()
	product?: Product;

	loadingMessage: string = 'Loading..'

	renderPrice() {
		return !this.product
			? this.loadingMessage
			: `${this.product.price} €`;
	}
}
