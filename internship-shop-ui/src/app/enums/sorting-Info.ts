import {Product} from "../interfaces/product";
import {SortType} from "./sort-by.enum";

export const SortingInfo = {
	[SortType.HIGHEST_PRICE]: {
		getComparison: (first: Product, second: Product): number => {
			return second.price - first.price;
		}
	},
	[SortType.LOWEST_PRICE]: {
		getComparison: (first: Product, second: Product): number => {
			return first.price - second.price;
		}
	},
	[SortType.NAME_ASCENDING]: {
		getComparison: (first: Product, second: Product): number => {
			return first.name.localeCompare(second.name);
		}
	},
	[SortType.NAME_DESCENDING]: {
		getComparison: (first: Product, second: Product): number => {
			return second.name.localeCompare(first.name);
		}
	},
}
