import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {HttpClientModule} from '@angular/common/http';
import {AppComponent} from './app.component';
import {ProductListComponent} from './components/product-list/product-list.component';
import {ProductComponent} from './components/product/product.component';
import {FormsModule} from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from "@angular/material/icon";
import {MatInputModule} from '@angular/material/input';
import {MatListModule} from '@angular/material/list';
import {MatCardModule} from '@angular/material/card';
import {MatToolbarModule} from '@angular/material/toolbar';
import {ProductInfoComponent} from "./components/product-info/product-info.component";
import {QuantityPickerComponent} from "./components/quantity-picker/quantity-picker.component";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {MatSelectModule} from "@angular/material/select";
import {MAT_FORM_FIELD_DEFAULT_OPTIONS} from "@angular/material/form-field";
import {FilterDialogComponent} from "./components/filter-dialog/filter-dialog.component";
import {MatSliderModule} from "@angular/material/slider";
import {MatDialogModule} from "@angular/material/dialog";
import {ProductFilterComponent} from "./components/product-filter/product-filter.component";
import {ProductSortComponent} from "./components/product-sort/product-sort.component";

@NgModule({
	declarations: [
		AppComponent,
		ProductListComponent,
		ProductComponent,
		ProductInfoComponent,
		QuantityPickerComponent,
		FilterDialogComponent,
		ProductFilterComponent,
		ProductSortComponent
	],
	imports: [
		BrowserModule,
		AppRoutingModule,
		HttpClientModule,
		FormsModule,
		BrowserAnimationsModule,
		MatButtonModule,
		MatIconModule,
		MatInputModule,
		MatListModule,
		MatCardModule,
		MatToolbarModule,
		MatSnackBarModule,
		MatSelectModule,
		MatSliderModule,
		MatDialogModule
	],
	providers: [
		{provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: {subscriptSizing: 'dynamic'}}
	],
	bootstrap: [AppComponent]
})
export class AppModule {

}
