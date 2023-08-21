import {Component, EventEmitter, Input, Output} from "@angular/core";
import {Product} from "../../interfaces/product";

@Component({
	selector: 'app-quantity-picker',
	templateUrl: './quantity-picker.component.html',
	styleUrls: ['./quantity-picker.component.scss']
})
export class QuantityPickerComponent {

	@Input()
	product?: Product;

	@Input()
	quantity!: number;

	@Output()
	onQuantityChanged = new EventEmitter<number>();

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
