import {Component, Inject} from '@angular/core';
import {IOrder} from "../../interfaces/order";
import {MatTableDataSource} from "@angular/material/table";
import {ShopService} from "../../services/shop.service";
import {MAT_DATE_LOCALE} from "@angular/material/core";
import {SnackBarService} from "../../services/snack-bar.service";

@Component({
	selector: 'app-statistics',
	templateUrl: './statistics.component.html',
	styleUrls: ['./statistics.component.scss']
})
export class StatisticsComponent {

	startDate: Date = new Date();
	endDate: Date = new Date();
	latestAllowedDate: Date = new Date();

	displayedColumns: string[] = ['id', 'userId', 'productId', 'quantity', 'unitPrice', 'originalPrice', 'profit', 'timestamp'];
	tableDataSource: MatTableDataSource<IOrder> = new MatTableDataSource(undefined);

	areStatisticsLoaded: boolean = false;

	lowProfitThreshold: number = 5;
	highProfitThreshold: number = 20;

	constructor(private shopService: ShopService,
							private snackBarService: SnackBarService,
							@Inject(MAT_DATE_LOCALE) private matDateLocale: string) {
		this.startDate.setMonth(this.startDate.getMonth() - 1);
		this.latestAllowedDate.setDate(this.latestAllowedDate.getDate() + 1);
	}

	setStartDate(date: Date): void {
		this.startDate = date;
	}

	setEndDate(date: Date): void {
		this.endDate = date;
	}

	getStatistics(): void {
		this.shopService.getOrdersBetweenDateTimes$(this.getFormattedDateTime(this.startDate), this.getFormattedDateTime(this.endDate))
			.subscribe((orders: IOrder[]): void => {
				if (orders.length === 0) {
					this.areStatisticsLoaded = false;
					this.snackBarService.displaySnackBar($localize`❌ No data exists for the selected time period`);
					return;
				}

				this.tableDataSource = new MatTableDataSource(orders);
				this.areStatisticsLoaded = true;
				this.snackBarService.displaySnackBar($localize`✅ Data for selected time period successfully loaded`);
			})
	}

	getOrderProfit(order: IOrder): number {
		if (!order) return 0;

		return +(order.quantity * (order.unitPrice - order.originalPrice)).toFixed(2);
	}

	getTotalProfit(): string {
		if (!this.tableDataSource.data) return '';

		return this.tableDataSource.data.map((order: IOrder) => order.quantity * (order.unitPrice - order.originalPrice))
			.reduce((acc: number, value: any) => acc + value, 0)
			.toFixed(2);
	}

	getFormattedDateTime(date: Date): string {
		return `${date.toLocaleDateString(this.matDateLocale)}T00:00`;
	}
}
