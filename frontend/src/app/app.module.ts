import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { SignupComponent } from './signup/signup.component';
import { SigninComponent } from './signin/signin.component';
import { HomeComponent } from './home/home.component';
import { AppRoutingModule } from './app.routing.module';
import { ReactiveFormsModule } from '@angular/forms';
import { StoreModule } from '@ngrx/store';
import { appReducer } from './store/reducers';
import { MessageComponent } from './shared/message/message.component';
import { AppHttpInterceptor } from './services/app-http-interceptor';
import { HeaderComponent } from './header/header.component';
import { NotificationsComponent } from './notifications/notifications.component';
import { MenuComponent } from './menu/menu.component';

@NgModule({
  declarations: [
    AppComponent,
    SignupComponent,
    SigninComponent,
    HomeComponent,
    MessageComponent,
    HeaderComponent,
    NotificationsComponent,
    MenuComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    StoreModule.forRoot(appReducer),
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AppHttpInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
