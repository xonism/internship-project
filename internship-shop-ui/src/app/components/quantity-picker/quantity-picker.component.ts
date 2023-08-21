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

	adjustQuantity(value: number) {
		if (this.product && this.product.quantity >= this.quantity + value && this.quantity + value >= 1) {
			this.quantity += value;
			this.onQuantityChanged.emit(this.quantity);
		}
	}
}
