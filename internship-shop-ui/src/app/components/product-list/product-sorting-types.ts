import {PropertyType} from "../../enums/property-type";

export const ProductSortingTypes = {
	PRICE_ASCENDING: {
		sortName: $localize`Price ↗`,
		isDescending: false,
		property: {
			name: 'price',
			type: PropertyType.NUMBER
		}
	},
	PRICE_DESCENDING: {
		sortName: $localize`Price ↘`,
		isDescending: true,
		property: {
			name: 'price',
			type: PropertyType.NUMBER
		}
	},
	NAME_ASCENDING: {
		sortName: $localize`Name ↗`,
		isDescending: false,
		property: {
			name: 'name',
			type: PropertyType.STRING
		}
	},
	NAME_DESCENDING: {
		sortName: $localize`Name ↘`,
		isDescending: true,
		property: {
			name: 'name',
			type: PropertyType.STRING
		}
	},
};
