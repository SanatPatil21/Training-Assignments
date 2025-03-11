import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Output } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { DummyDbService } from '../../services/dummy-db.service';


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginForm!: FormGroup;
  loginError: string = '';

  constructor(private dummyDbService: DummyDbService) {}

  @Output() loginSuccess = new EventEmitter<void>();


  ngOnInit() {
    this.loginForm = new FormGroup({
      uname: new FormControl('', [Validators.required]),  // Change from 'username' to 'uname'
      password: new FormControl('', [Validators.required])
    });
    
  }



  submitLogin() {
    if (this.loginForm.valid) {
      const result = this.dummyDbService.loginUser(this.loginForm.value);
      if (result) {
        console.log('Login successful!');
        this.loginError = '';

        this.loginSuccess.emit();
      } else {
        this.loginError = 'Invalid username or password';
      }
    }
  }



}
