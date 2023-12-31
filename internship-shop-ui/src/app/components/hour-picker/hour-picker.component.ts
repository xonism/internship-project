import {Component, EventEmitter, Input, Output} from "@angular/core";

@Component({
	selector: 'app-hour-picker',
	templateUrl: './hour-picker.component.html',
	styleUrls: ['./hour-picker.component.scss']
})
export class HourPickerComponent {

	@Input()
	selectedHour!: number;

	@Output()
	onSelectedHourChange: EventEmitter<number> = new EventEmitter<number>();

	emitChange(): void {
		this.onSelectedHourChange.emit(this.selectedHour);
	}
}
