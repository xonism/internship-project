import {Component, EventEmitter, Input, Output} from "@angular/core";

@Component({
	selector: 'app-date-picker',
	templateUrl: './date-picker.component.html',
	styleUrls: ['./date-picker.component.scss']
})
export class DatePickerComponent {

	@Input()
	date!: Date;

	@Input()
	maxDate!: Date;

	@Output()
	onDateChange: EventEmitter<Date> = new EventEmitter<Date>();

	emitChange(): void {
		this.onDateChange.emit(this.date);
	}
}
