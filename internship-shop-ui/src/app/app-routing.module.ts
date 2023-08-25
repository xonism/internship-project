import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ProductListComponent} from './components/product-list/product-list.component';
import {ProductComponent} from './components/product/product.component';
import {ReportComponent} from "./components/report/report.component";

const routes: Routes = [
	{path: 'product/:id', component: ProductComponent},
	{path: 'reports', component: ReportComponent},
	{path: 'products', component: ProductListComponent, pathMatch: 'full'},
	{path: '**', redirectTo: '/products', pathMatch: 'full'}
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule {
}
