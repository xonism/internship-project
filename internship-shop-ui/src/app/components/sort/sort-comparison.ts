import {IProduct} from "../../interfaces/product";
import {SortType} from "../../enums/sort-type";

export const SortComparison = {
	[SortType.HIGHEST_PRICE]: { // TODO: number ascending
		getComparison: (first: IProduct, second: IProduct): number => {
			return second.price - first.price;
		}
	},
	[SortType.LOWEST_PRICE]: {
		getComparison: (first: IProduct, second: IProduct): number => {
			return first.price - second.price;
		}
	},
	[SortType.NAME_ASCENDING]: { // TODO: text ascending
		getComparison: (first: IProduct, second: IProduct): number => {
			return first.name.localeCompare(second.name);
		}
	},
	[SortType.NAME_DESCENDING]: {
		getComparison: (first: IProduct, second: IProduct): number => {
			return second.name.localeCompare(first.name);
		}
	},
}
