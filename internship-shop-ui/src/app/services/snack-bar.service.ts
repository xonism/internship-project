import {Injectable} from "@angular/core";
import {MatSnackBar} from "@angular/material/snack-bar";

@Injectable({
	providedIn: 'root'
})
export class SnackBarService {

	private duration: number = 5_000;

	private config = {
		duration: this.duration
	}

	private action: string = "Close";

	constructor(private matSnackBar: MatSnackBar) {

	}

	displaySuccessfulOrderSnackBar() {
		this.matSnackBar.open(
			"✅ Order successful",
			this.action,
			this.config
		);
	}

	displayErrorOrderSnackBar() {
		this.matSnackBar.open(
			"❌ Error occurred",
			this.action,
			this.config
		);
	}
}
