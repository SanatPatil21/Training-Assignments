import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { DummyDbService } from '../../services/dummy-db.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, CommonModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  userForm: any;
  constructor(public dummyDbService: DummyDbService) { 
    console.log('cons called');
  }


  ngOnInit() {
    this.userForm = new FormGroup({
      cid: new FormControl('', [Validators.required, Validators.pattern(/^\d{7}$/), this.validateCid.bind(this)]),
      uname: new FormControl('', [
        Validators.required,
        Validators.minLength(5),
        Validators.pattern(/^[A-Za-z]+$/) 
      ]),
      password: new FormControl('', [
        Validators.required,
        Validators.minLength(8),
        Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\W).*$/) 
      ]),

      confirmPassword: new FormControl('', [Validators.required]),

      accountNumber: new FormControl('', [
        Validators.required,
        Validators.pattern(/^\d{6,}$/) 
      ])
    }, { validators: this.passwordMatchValidator }
  ); 
  }

  validateCid(control: any):any{
    if(this.dummyDbService.users.find(u => u.cid === control.value)){
      return { cidExists: true };
    }
    return null;
  }

  passwordMatchValidator(form: any) {
    const password = form.get('password')?.value;
    const confirmPassword = form.get('confirmPassword')?.value;
    return password === confirmPassword ? null : { passwordMismatch: true };
  }




  submitForm(obj: any) {
    if (this.userForm.valid) {
      console.log(obj);
      console.log(this.dummyDbService);
      
      
      this.dummyDbService.addUser(obj);
      this.userForm.reset();
    }
  }





}
