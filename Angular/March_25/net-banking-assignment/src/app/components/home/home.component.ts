import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { LoginComponent } from "../login/login.component";
import { RegisterComponent } from "../register/register.component";
import { DashboardComponent } from "../dashboard/dashboard.component";
import { DummyDbService } from '../../services/dummy-db.service';

@Component({
  selector: 'app-home',
  imports: [CommonModule, LoginComponent, RegisterComponent, DashboardComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  loginVisible:boolean = false;
  registerVisible:boolean = false;
  homeVisible:boolean = true;
  loggedIn: boolean = false;


  constructor(private dummyDbService: DummyDbService) { }

  ngOnInit() {
    if(this.dummyDbService.loggedInUser !== null) this.loggedIn = true;
  }

  loadHome(){

    this.registerVisible = false;
    this.homeVisible = true;
    this.loginVisible = false;
    if(this.dummyDbService.loggedInUser !== null) this.loggedIn = true;

  }

  loadRegister() {
    if(this.loggedIn) {
      alert('You are already logged in!')
      return;
    };
    this.registerVisible = true;
    this.homeVisible = false;
    this.loginVisible = false;
    
  }

  loadLogin() {
    if(this.loggedIn) {
      alert('You are already logged in!')
      return;
    };
    this.registerVisible = false;
    this.homeVisible = false;
    this.loginVisible = true;
  }

}
