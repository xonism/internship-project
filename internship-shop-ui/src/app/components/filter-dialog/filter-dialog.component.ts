import {Component, Inject} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {IFilterDialogData} from "../../interfaces/filter-dialog-data";

@Component({
	selector: 'app-filter-dialog',
	templateUrl: './filter-dialog.component.html',
	styleUrls: ['./filter-dialog.component.scss']
})
export class FilterDialogComponent {

	minRange: number;
	maxRange: number;

	constructor(private dialogRef: MatDialogRef<FilterDialogComponent>,
							@Inject(MAT_DIALOG_DATA) public data: IFilterDialogData) {
		this.minRange = data.minRange;
		this.maxRange = data.maxRange;
	}

	close(): void {
		this.dialogRef.close();
	}
}
