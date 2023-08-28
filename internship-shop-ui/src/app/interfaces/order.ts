export interface IOrder {
	id: number,
	userId: number,
	productId: number,
	quantity: number,
	unitPrice: number,
	originalPrice: number,
	timestamp: Date
}
