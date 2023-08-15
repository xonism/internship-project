import { Component, OnDestroy, OnInit } from "@angular/core";
import { Product } from "src/app/interfaces/product";
import { WarehouseService } from '../../services/warehouse.service';
import { Subscription } from 'rxjs';

@Component({
    selector: 'insh-product-list',
    templateUrl: './product-list.component.html',
    styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit, OnDestroy {
    private subscription: Subscription = Subscription.EMPTY;
    
    products: Product[] | null = null;

    constructor(private warehouseService: WarehouseService) {
  
    }

    ngOnInit(): void {
        this.subscription = this.warehouseService.products$().subscribe(products => {
            this.products = products;
        });
    }

    ngOnDestroy(): void {
        this.subscription.unsubscribe();
    }
}
