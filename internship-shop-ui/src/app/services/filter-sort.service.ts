import {EventEmitter, Injectable, Output} from '@angular/core';

@Injectable({providedIn: 'root'})
export class FilterSortService {

	@Output()
	filterChange: EventEmitter<undefined> = new EventEmitter<undefined>();

	emitFilterChange(): void {
		this.filterChange.emit(undefined);
	}
}
