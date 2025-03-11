import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})


export class DummyDbService {
  public loggedIn: boolean = false;

  constructor() { }

  users: any[] = [];
  loggedInUser: any = null;

  addUser(user: any) :void{
    this.users.push(user);
    console.log('List of Users');
    console.log(this.users);

  }

  loginUser(user: any) {
    console.log('Attempting login:', user);
    console.log('Existing users:', this.users);
  
    const foundUser = this.users.find(
      u => u.uname === user.uname 
    );
  
    if (foundUser) {
      this.loggedIn = true;
      this.loggedInUser ={
        username: foundUser.uname,
        accountNo: foundUser.accountNumber
      }
      console.log('Login successful!', foundUser);
      return true;
    } else {
      console.log('Login failed!');
      return false;
    }
  }
  

  getUsers(){
    return this.users;
  }
}
