import {Component, EventEmitter, Input, Output} from "@angular/core";
import {SortBy} from "../../enums/sort-by.enum";

@Component({
	selector: 'app-sort',
	templateUrl: './sort.component.html',
	styleUrls: ['./sort.component.scss']
})
export class SortComponent {

	@Input()
	selectedSort!: SortBy;

	@Input()
	sortingOptions!: string[];

	@Output()
	selectedSortChange: EventEmitter<SortBy> = new EventEmitter<SortBy>();

	emitSortChange() {
		this.selectedSortChange.emit(this.selectedSort);
	}
}
