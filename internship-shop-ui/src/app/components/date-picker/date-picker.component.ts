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
	onDateChange: EventEmitter<Date> = new EventEmitter<Date>();

	emitChange(): void {
		this.onDateChange.emit(this.selectedDate);
	}
}
