import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {ISortTypeInfo} from "../../interfaces/sort-type-info";
import {SortService} from "../../services/sort.service";
import {StateChangeService} from "../../services/state-change.service";

@Component({
	selector: 'app-sort',
	templateUrl: './sort.component.html',
	styleUrls: ['./sort.component.scss']
})
export class SortComponent implements OnInit {

	@Input()
	selectedSort!: ISortTypeInfo;

	@Input()
	sortingOptions!: ISortTypeInfo[];

	@Input()
	elements!: any[];

	@Input()
	processedElements!: any[];

	@Output()
	elementsChange: EventEmitter<any[]> = new EventEmitter<any[]>();

	@Output()
	selectedSortChange: EventEmitter<ISortTypeInfo> = new EventEmitter<ISortTypeInfo>();

	sortNames!: string[];

	constructor(private sortService: SortService, private filterSortService: StateChangeService) {

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

	emitSortedElements(elements: any[], sortType: ISortTypeInfo): void {
		this.elementsChange.emit(this.sortService.getSortedElements(elements, sortType));
	}

	ngOnInit(): void {
		this.sortNames = this.sortingOptions.map((sortTypeInfo: ISortTypeInfo) => sortTypeInfo.sortName);

		this.emitSortedElements(this.processedElements, this.selectedSort);

		this.filterSortService.filterChange.subscribe((): void => {
			this.emitSortedElements(this.elements, this.selectedSort);
		});
	}
}
