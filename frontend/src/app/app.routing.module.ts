import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { HomeComponent } from "./home/home.component";
import { ConnectionGuard } from "./services/connection.guard";
import { SigninComponent } from "./signin/signin.component";
import { SignupComponent } from "./signup/signup.component";

const routes: Routes = [
    { path: 'sign-up', component: SignupComponent},
    { path: 'sign-in', component: SigninComponent},
    { path: '', component: HomeComponent, canActivate: [ConnectionGuard]}
];

@NgModule({
    imports: [
        RouterModule.forRoot(routes, {
            useHash: true
        })
    ],
    exports: [RouterModule]
})
export class AppRoutingModule {}