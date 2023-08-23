import {Injectable} from '@angular/core';
import {ISortTypeInfo} from "../interfaces/sort-type-info";

@Injectable({providedIn: 'root'})
export class SortService {

	getSortedElements(elements: any[], sortType: ISortTypeInfo): any[] | undefined {
		if (!sortType) return;

		const {isDescending, property}: ISortTypeInfo = sortType;

		return elements.sort((firstElement: any, secondElement: any): number | any => {
			if (property.type === 'number') {
				return isDescending
					? secondElement[property.name] - firstElement[property.name]
					: firstElement[property.name] - secondElement[property.name];
			}

			if (property.type === 'string') {
				return isDescending
					? secondElement[property.name].localeCompare(firstElement[property.name])
					: firstElement[property.name].localeCompare(secondElement[property.name]);
			}
		});
	}
}
