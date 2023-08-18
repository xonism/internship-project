import {Component, EventEmitter, Input, Output} from "@angular/core";
import { map } from 'rxjs';
import { ShopService } from "src/app/services/shop.service";
import {Product} from "../../interfaces/product";

@Component({
	selector: 'insh-quantity-picker',
	templateUrl: './quantity-picker.component.html',
	styleUrls: ['./quantity-picker.component.scss']
})
export class QuantityPickerComponent {

	@Input()
	product: Product | null = null;

	@Output()
	onQuantityChanged = new EventEmitter<number>();

	quantity: number = 1;

	decrementQuantity() {
		if (this.quantity > 1) {
			this.quantity -= 1;
			this.onQuantityChanged.emit(this.quantity);
		}
	}

	incrementQuantity() {
		if (this.product && this.quantity < this.product.quantity) {
			this.quantity += 1;
			this.onQuantityChanged.emit(this.quantity);
		}
	}
}