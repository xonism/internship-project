import {Component, EventEmitter, Input, OnDestroy, Output} from "@angular/core";
import {DialogPriceData} from "../../interfaces/dialog-price-data";
import {FilterDialogComponent} from "../filter-dialog/filter-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {Subscription} from "rxjs";

@Component({
	selector: 'app-product-filter',
	templateUrl: './product-filter.component.html',
	styleUrls: ['./product-filter.component.scss']
})
export class ProductFilterComponent implements OnDestroy {

	private subscription: Subscription = Subscription.EMPTY;

	@Input()
	min!: number;

	@Input()
	max!: number;

	@Output()
	filterValuesChange: EventEmitter<DialogPriceData> = new EventEmitter<DialogPriceData>();

	isFilterApplied: boolean = false;

	constructor(public dialog: MatDialog) {

	}

	openFilterDialog(): void {
		const filterRange: DialogPriceData = {
			min: this.min,
			max: this.max
		}

		const dialogRef = this.dialog.open(
			FilterDialogComponent,
			{data: filterRange}
		);

		this.subscription = dialogRef.afterClosed()
			.subscribe((result) => {
				if (!result) return;

				this.isFilterApplied = true;
				this.filterValuesChange.emit({
					min: result[0],
					max: result[1]
				});
			})
	}

	removeFilters(): void {
		this.isFilterApplied = false;
		this.filterValuesChange.emit({
			min: 0,
			max: 0
		});
	}

	ngOnDestroy(): void {
		this.subscription.unsubscribe();
	}
}
