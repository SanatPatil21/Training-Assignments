import { Component } from '@angular/core';
import { DummyDbService } from '../../services/dummy-db.service';

@Component({
  selector: 'app-dashboard',
  imports: [],
  standalone: true,
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {
  loggedInUser: any = null

  constructor(private dummyDbService: DummyDbService) { 
    this.loggedInUser = this.dummyDbService.loggedInUser;
    console.log(this.loggedInUser);
    
  }



}
