export const ProductSortingTypes = {
	PRICE_ASCENDING: {
		sortName: 'Price ascending',
		isDescending: false,
		property: {
			name: 'price',
			type: 'number'
		}
	},
	PRICE_DESCENDING: {
		sortName: 'Price descending',
		isDescending: true,
		property: {
			name: 'price',
			type: 'number'
		}
	},
	NAME_ASCENDING: {
		sortName: 'Name ascending',
		isDescending: false,
		property: {
			name: 'name',
			type: 'string'
		}
	},
	NAME_DESCENDING: {
		sortName: 'Name descending',
		isDescending: true,
		property: {
			name: 'name',
			type: 'string'
		}
	},
};
