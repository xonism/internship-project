import {Component, Input} from "@angular/core";
import {IProduct} from "../../interfaces/product";

@Component({
	selector: 'app-product-info',
	templateUrl: './product-info.component.html',
	styleUrls: ['./product-info.component.scss']
})
export class ProductInfoComponent {

	@Input()
	product?: IProduct;

	loadingMessage: string = 'Loading..'

	renderPrice() {
		return !this.product
			? this.loadingMessage
			: `${this.product.price} €`;
	}
}
