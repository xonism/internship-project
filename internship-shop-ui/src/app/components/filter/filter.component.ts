import {Component, EventEmitter, Input, OnDestroy, Output} from "@angular/core";
import {IFilterDialogData} from "../../interfaces/filter-dialog-data";
import {FilterDialogComponent} from "../filter-dialog/filter-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {Subscription} from "rxjs";
import {FilterService} from "../../services/filter.service";
import {StateChangeService} from "../../services/state-change.service";

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

	constructor(public dialog: MatDialog,
							private filterService: FilterService,
							private filterSortService: StateChangeService) {

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
				this.elementsChange.emit(this.filterService.getFilteredElements(this.elements, filterCriteria));
			})
	}

	removeFilters(): void {
		this.isFilterApplied = false;
		this.filterSortService.emitChange();
	}

	ngOnDestroy(): void {
		this.subscription.unsubscribe();
	}
}
