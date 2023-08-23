import {Product} from "../interfaces/product";
import {SortBy} from "./sort-by.enum";

export const SortingInfo = {
	[SortBy.HIGHEST_PRICE]: {
		sort: (first: Product, second: Product): number => {
			return second.price - first.price;
		}
	},
	[SortBy.LOWEST_PRICE]: {
		sort: (first: Product, second: Product): number => {
			return first.price - second.price;
		}
	},
	[SortBy.NAME_ASCENDING]: {
		sort: (first: Product, second: Product): number => {
			return first.name.localeCompare(second.name);
		}
	},
	[SortBy.NAME_DESCENDING]: {
		sort: (first: Product, second: Product): number => {
			return second.name.localeCompare(first.name);
		}
	},
}
