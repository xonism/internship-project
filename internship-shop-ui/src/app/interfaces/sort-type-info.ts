import {PropertyType} from "../components/enums/property-type";

export interface ISortTypeInfo {
	sortName: string,
	isDescending: boolean,
	property: {
		name: string,
		type: PropertyType
	}
}
