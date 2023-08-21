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
		const isNegativeValueAndSufficientPickerQuantity = value < 0 && this.quantity > 1;
		const isPositiveValueAndSufficientProductQuantity =
			value > 0 && this.product && this.quantity < this.product.quantity;
		if (isNegativeValueAndSufficientPickerQuantity || isPositiveValueAndSufficientProductQuantity) {
			this.quantity += value;
		}
		this.onQuantityChanged.emit(this.quantity);
	}
}
