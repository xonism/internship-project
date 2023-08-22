import {Component, EventEmitter, Input, Output} from "@angular/core";
import {SortingOptions} from "../../enums/sorting-options.enum";

@Component({
	selector: 'app-product-sort',
	templateUrl: './product-sort.component.html',
	styleUrls: ['./product-sort.component.scss']
})
export class ProductSortComponent {

	@Input()
	selectedSort!: string;

	@Output()
	selectedSortChange: EventEmitter<string> = new EventEmitter<string>();

	sortingOptions: string[] = Object.values(SortingOptions);

	emitSortChange() {
		this.selectedSortChange.emit(this.selectedSort);
	}
}
