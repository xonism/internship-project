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

	private action: string = $localize`Close`;

	constructor(private matSnackBar: MatSnackBar) {

	}

	displaySnackBar(message: string) {
		this.matSnackBar.open(
			message,
			this.action,
			this.config
		);
	}
}
