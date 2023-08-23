import {Component, EventEmitter, Input, Output} from "@angular/core";
import {SortBy} from "../../enums/sort-by.enum";

@Component({
	selector: 'app-product-sort',
	templateUrl: './product-sort.component.html',
	styleUrls: ['./product-sort.component.scss']
})
export class ProductSortComponent {

	@Input()
	selectedSort!: SortBy;

	@Output()
	selectedSortChange: EventEmitter<SortBy> = new EventEmitter<SortBy>();

	sortingOptions: string[] = Object.values(SortBy);

	emitSortChange() {
		this.selectedSortChange.emit(this.selectedSort);
	}
}
