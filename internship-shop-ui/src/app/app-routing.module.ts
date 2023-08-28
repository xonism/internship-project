import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ProductListComponent} from './components/product-list/product-list.component';
import {ProductComponent} from './components/product/product.component';
import {ReportComponent} from "./components/report/report.component";
import {StatisticsComponent} from "./components/statistics/statistics.component";

const routes: Routes = [
	{path: 'product/:id', component: ProductComponent},
	{path: 'products', component: ProductListComponent, pathMatch: 'full'},
	{path: 'reports', component: ReportComponent},
	{path: 'statistics', component: StatisticsComponent},
	{path: '**', redirectTo: '/products', pathMatch: 'full'}
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule {
}
