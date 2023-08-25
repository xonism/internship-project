import {Component, EventEmitter, Input, Output} from "@angular/core";

@Component({
	selector: 'app-date-picker',
	templateUrl: './date-picker.component.html',
	styleUrls: ['./date-picker.component.scss']
})
export class DatePickerComponent {

	@Input()
	selectedDate!: Date;

	@Input()
	latestAllowedDate!: Date;

	@Output()
	onSelectedDateChange: EventEmitter<Date> = new EventEmitter<Date>();

	emitChange(): void {
		this.onSelectedDateChange.emit(this.selectedDate);
	}
}
