import {EventEmitter, Injectable, Output} from '@angular/core';

@Injectable({providedIn: 'root'})
export class StateChangeService {

	@Output()
	filterChange: EventEmitter<undefined> = new EventEmitter<undefined>();

	emitChange(): void {
		this.filterChange.emit(undefined);
	}
}
