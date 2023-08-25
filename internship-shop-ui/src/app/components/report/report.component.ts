import {Component, Inject, OnDestroy} from '@angular/core';
import {ShopService} from '../../services/shop.service';
import {Subscription} from 'rxjs';
import {SnackBarService} from '../../services/snack-bar.service';
import {saveAs} from 'file-saver';
import {HttpResponse} from '@angular/common/http';
import {MAT_DATE_LOCALE} from "@angular/material/core";

@Component({
	selector: 'app-report',
	templateUrl: './report.component.html',
	styleUrls: ['./report.component.scss']
})
export class ReportComponent implements OnDestroy {

	private subscriptions: Subscription[] = [];

	selectedDate: Date = new Date();
	latestAllowedDate: Date = new Date();

	selectedHour: number = new Date().getHours() - 1;

	reportData: string[][] = [];
	isReportLoaded: boolean = false;

	displayedColumns: string[] = ['id', 'userId', 'productId', 'quantity', 'unitPrice', 'timestamp'];

	constructor(private shopService: ShopService,
							private snackBarService: SnackBarService,
							@Inject(MAT_DATE_LOCALE) private matDateLocale: any) {

	}

	ngOnDestroy(): void {
		this.subscriptions.forEach((subscription: Subscription): void => {
			subscription.unsubscribe();
		});
	}

	setSelectedHour(hour: number): void {
		this.selectedHour = hour;
	}

	setSelectedDate(date: Date): void {
		this.selectedDate = date;
	}

	downloadReport(): void {
		const subscription: Subscription = this.shopService.getReportDataBlob$(this.getFormattedDateTime())
			.subscribe((blobResponse: HttpResponse<Blob>): void => {
				if (!blobResponse.body) return;

				const filename: string | undefined = blobResponse.headers.get('content-disposition')?.split('; filename=')[1];
				saveAs(blobResponse.body, filename);
			});
		this.subscriptions.push(subscription);
	}

	getReport(): void {
		if (this.selectedDate === new Date() && this.selectedHour > new Date().getHours() - 1) {
			this.snackBarService.displaySnackBar($localize`❌ Report for selected hour isn\'t generated yet`);
			return;
		}

		const subscription: Subscription = this.shopService.getReportData$(this.getFormattedDateTime())
			.subscribe((reportData: string): void => {
				const processedReportData: string[][] = this.getFormattedReportData(reportData);

				if (processedReportData.length === 0) {
					this.isReportLoaded = false;
					this.snackBarService.displaySnackBar($localize`❌ No orders were made during selected hour`);
					return;
				}

				this.reportData = processedReportData;
				this.isReportLoaded = true;
			});
		this.subscriptions.push(subscription);
	}

	getFormattedReportData(reportData: string): string[][] {
		const result: string[][] = [];
		reportData.split('\n')
			.forEach((line: string, index: number): void => {
				if (index == 0) return;

				result.push(line.replaceAll('\"', '').split(','));
			})
		result.pop();
		return result;
	}

	getFormattedDateTime(): string {
		const date: Date = new Date(this.selectedDate.getFullYear(), this.selectedDate.getMonth(), this.selectedDate.getDate(), this.selectedHour);
		return `${date.toLocaleDateString(this.matDateLocale)}T${date.toLocaleTimeString(this.matDateLocale)}`;
	}
}
