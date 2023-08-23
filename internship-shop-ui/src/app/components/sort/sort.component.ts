import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {ISortTypeInfo} from "../../interfaces/sort-type-info";
import {SortService} from "../../services/sort.service";

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

	@Output()
	elementsChange: EventEmitter<any[]> = new EventEmitter<any[]>();

	@Output()
	selectedSortChange: EventEmitter<ISortTypeInfo> = new EventEmitter<ISortTypeInfo>();

	sortNames!: string[];

	constructor(private sortService: SortService) {

	}

	emitSortChange(sortName: string): void {
		this.emitSelectedSort(sortName);
		this.emitSortedElements();
	}

	emitSelectedSort(sortName: string): void {
		const sortingOption: ISortTypeInfo | undefined = structuredClone(this.sortingOptions)
			.find((sortingOption: ISortTypeInfo): boolean => sortingOption.sortName === sortName);

		if (sortingOption) {
			this.selectedSort = sortingOption;
		}

		this.selectedSortChange.emit(this.selectedSort);
	}

	emitSortedElements(): void {
		const sortedElements: undefined | any[] = this.sortService.getSortedElements(this.elements, this.selectedSort);
		this.elementsChange.emit(sortedElements);
	}

	ngOnInit(): void {
		this.sortingOptions = structuredClone(this.sortingOptions);
		this.sortNames = this.sortingOptions.map((sortTypeInfo: ISortTypeInfo) => sortTypeInfo.sortName);

		this.emitSortedElements();
	}
}
