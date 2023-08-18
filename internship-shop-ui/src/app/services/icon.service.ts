import { MatIconRegistry } from "@angular/material/icon";
import { DomSanitizer } from "@angular/platform-browser";
import { Icons } from "./icons.enum";
import { Injectable } from "@angular/core";

@Injectable({
    providedIn: 'root'
})
export class IconService {

    constructor(private matIconRegistry: MatIconRegistry, private sanitizer: DomSanitizer) {

    }

    registerIcons() {
        this.load(Icons, 'assets/icons');
    }

    private load(icons: typeof Icons, url: string) {
        Object.keys(icons).forEach((icon) => {
            this.matIconRegistry.addSvgIcon(
                icon,
                this.sanitizer.bypassSecurityTrustResourceUrl(`${url}/${icon}.svg`))
        })
    }
}