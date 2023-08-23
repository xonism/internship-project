import {Component, EventEmitter, Input, OnDestroy, Output} from "@angular/core";
import {IFilterDialogData} from "../../interfaces/filter-dialog-data";
import {FilterDialogComponent} from "../filter-dialog/filter-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {Subscription} from "rxjs";
import {FilterService} from "../../services/filter.service";

@Component({
	selector: 'app-filter',
	templateUrl: './filter.component.html',
	styleUrls: ['./filter.component.scss']
})
export class FilterComponent implements OnDestroy {

	private subscription: Subscription = Subscription.EMPTY;

	@Input()
	minRange!: number;

	@Input()
	maxRange!: number;

	@Input()
	elements!: any[];

	@Input()
	filterDialogData!: IFilterDialogData;

	@Output()
	elementsChange: EventEmitter<any[]> = new EventEmitter<any[]>();

	isFilterApplied: boolean = false;

	constructor(public dialog: MatDialog, private filterService: FilterService) {

	}

	openFilterDialog(): void {
		const dialogRef = this.dialog.open(
			FilterDialogComponent,
			{data: this.filterDialogData}
		);

		this.subscription = dialogRef.afterClosed()
			.subscribe((filterCriteria): void => {
				if (!filterCriteria) return;

				this.isFilterApplied = true;
				const filteredElements: any[] = this.filterService.getFilteredElements(this.elements, filterCriteria);
				this.elementsChange.emit(filteredElements);
			})
	}

	removeFilters(): void {
		this.isFilterApplied = false;
		this.elementsChange.emit(undefined);
	}

	ngOnDestroy(): void {
		this.subscription.unsubscribe();
	}
}
