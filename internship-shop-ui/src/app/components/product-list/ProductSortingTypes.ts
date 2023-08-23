import {PropertyType} from "../enums/property-type";

export const ProductSortingTypes = {
	PRICE_ASCENDING: {
		sortName: 'Price ascending',
		isDescending: false,
		property: {
			name: 'price',
			type: PropertyType.NUMBER
		}
	},
	PRICE_DESCENDING: {
		sortName: 'Price descending',
		isDescending: true,
		property: {
			name: 'price',
			type: PropertyType.NUMBER
		}
	},
	NAME_ASCENDING: {
		sortName: 'Name ascending',
		isDescending: false,
		property: {
			name: 'name',
			type: PropertyType.STRING
		}
	},
	NAME_DESCENDING: {
		sortName: 'Name descending',
		isDescending: true,
		property: {
			name: 'name',
			type: PropertyType.STRING
		}
	},
};
