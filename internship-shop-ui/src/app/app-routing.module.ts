import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ProductListComponent} from './components/product-list/product-list.component';
import {ProductComponent} from './components/product/product.component';

const routes: Routes = [
	{path: 'product/:id', component: ProductComponent},
	{path: 'product-list', component: ProductListComponent, pathMatch: 'full'},
	{path: '', redirectTo: '/product-list', pathMatch: 'full'}
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule {
}
