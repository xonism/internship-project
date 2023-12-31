import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {ISortTypeInfo} from "../../interfaces/sort-type-info";
import {SortService} from "../../services/sort.service";
import {StateChangeService} from "../../services/state-change.service";

@Component({
	selector: 'app-sort',
	templateUrl: './sort.component.html',
	styleUrls: ['./sort.component.scss']
})
export class SortComponent<T> implements OnInit {

	@Input()
	selectedSort!: ISortTypeInfo;

	@Input()
	sortingOptions!: ISortTypeInfo[];

	@Input()
	elements!: T[];

	@Input()
	processedElements!: T[];

	@Output()
	elementsChange: EventEmitter<T[]> = new EventEmitter<T[]>();

	@Output()
	selectedSortChange: EventEmitter<ISortTypeInfo> = new EventEmitter<ISortTypeInfo>();

	sortNames!: string[];

	constructor(private sortService: SortService, private stateChangeService: StateChangeService) {

	}

	emitSortChange(sortName: string): void {
		this.emitSelectedSort(sortName);
		this.emitSortedElements(this.processedElements, this.selectedSort);
	}

	emitSelectedSort(sortName: string): void {
		const sortingOption: ISortTypeInfo | undefined = this.sortingOptions.find(
			(sortingOption: ISortTypeInfo): boolean => sortingOption.sortName === sortName);

		if (sortingOption) {
			this.selectedSort = sortingOption;
		}

		this.selectedSortChange.emit(this.selectedSort);
	}

	emitSortedElements(elements: T[], sortType: ISortTypeInfo): void {
		this.elementsChange.emit(this.sortService.getSortedElements(elements, sortType));
	}

	ngOnInit(): void {
		this.sortNames = this.sortingOptions.map((sortTypeInfo: ISortTypeInfo) => sortTypeInfo.sortName);

		this.emitSortedElements(this.processedElements, this.selectedSort);

		this.stateChangeService.filterChange.subscribe((): void => {
			this.emitSortedElements(this.elements, this.selectedSort);
		});
	}
}
