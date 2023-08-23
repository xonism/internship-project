import {Component, EventEmitter, Input, OnDestroy, Output} from "@angular/core";
import {FilterDialogData} from "../../interfaces/filter-dialog-data";
import {FilterDialogComponent} from "../filter-dialog/filter-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {Subscription} from "rxjs";

@Component({
	selector: 'app-filter',
	templateUrl: './filter.component.html',
	styleUrls: ['./filter.component.scss']
})
export class FilterComponent implements OnDestroy {

	private subscription: Subscription = Subscription.EMPTY;

	@Input()
	min!: number;

	@Input()
	max!: number;

	@Output()
	filterValueChange: EventEmitter<FilterDialogData> = new EventEmitter<FilterDialogData>();

	isFilterApplied: boolean = false;

	constructor(public dialog: MatDialog) {

	}

	openFilterDialog(): void {
		const filterDialogData: FilterDialogData = {
			minPrice: this.min,
			maxPrice: this.max
		}

		const dialogRef = this.dialog.open(
			FilterDialogComponent,
			{data: filterDialogData}
		);

		this.subscription = dialogRef.afterClosed()
			.subscribe((result): void => {
				if (!result) return;

				this.isFilterApplied = true;
				this.filterValueChange.emit({
					minPrice: result[0],
					maxPrice: result[1]
				});
			})
	}

	removeFilters(): void {
		this.isFilterApplied = false;
		this.filterValueChange.emit({
			minPrice: 0,
			maxPrice: 0
		});
	}

	ngOnDestroy(): void {
		this.subscription.unsubscribe();
	}
}
