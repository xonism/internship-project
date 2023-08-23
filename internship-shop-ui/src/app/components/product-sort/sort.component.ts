import {Component, EventEmitter, Input, Output} from "@angular/core";
import {SortType} from "../../enums/sort-by.enum";

@Component({
	selector: 'app-sort',
	templateUrl: './sort.component.html',
	styleUrls: ['./sort.component.scss']
})
export class SortComponent {

	@Input()
	selectedSort!: SortType;

	@Input()
	sortingOptions!: string[];

	@Output()
	selectedSortChange: EventEmitter<SortType> = new EventEmitter<SortType>();

	emitSortChange() {
		this.selectedSortChange.emit(this.selectedSort);
	}
}
