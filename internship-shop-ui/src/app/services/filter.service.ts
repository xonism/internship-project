import {Injectable} from '@angular/core';

@Injectable({providedIn: 'root'})
export class FilterService {

	getFilteredElements(elements: any[], filterCriteria: number[]): any[] {
		return elements.filter(
			(element: any) => element.price >= filterCriteria[0] && element.price <= filterCriteria[1]);
	}
}
