import { NgModule, LOCALE_ID } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { registerLocaleData } from '@angular/common';
import locale from '@angular/common/locales/en';

import {
    RfbLoyaltySharedLibsModule,
    JhiAlertComponent,
    JhiAlertErrorComponent
} from './';

@NgModule({
    imports: [
        RfbLoyaltySharedLibsModule
    ],
    declarations: [
        JhiAlertComponent,
        JhiAlertErrorComponent
    ],
    providers: [
        Title,
        {
            provide: LOCALE_ID,
            useValue: 'en'
        },
    ],
    exports: [
        RfbLoyaltySharedLibsModule,
        JhiAlertComponent,
        JhiAlertErrorComponent
    ]
})
export class RfbLoyaltySharedCommonModule {
    constructor() {
        registerLocaleData(locale);
    }
}
