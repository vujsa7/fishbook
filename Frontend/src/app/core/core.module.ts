import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { AuthService } from './authentication/auth.service';
import { BaseApiService } from './services/base-api.service';

@NgModule({
  imports: [
    CommonModule
  ],
  providers: [
    BaseApiService,
  ]
})
export class CoreModule { }
