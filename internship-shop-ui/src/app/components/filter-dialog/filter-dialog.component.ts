import {Component, Inject} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {DialogPriceData} from "../../interfaces/dialog-price-data";

@Component({
	selector: 'app-filter-dialog',
	templateUrl: './filter-dialog.component.html',
	styleUrls: ['./filter-dialog.component.scss']
})
export class FilterDialogComponent {

	min: number;
	max: number;

	constructor(
		private dialogRef: MatDialogRef<FilterDialogComponent>,
		@Inject(MAT_DIALOG_DATA) public data: DialogPriceData
	) {
		this.min = data.min;
		this.max = data.max;
	}

	onDialogClose(): void {
		this.dialogRef.close();
	}
}
