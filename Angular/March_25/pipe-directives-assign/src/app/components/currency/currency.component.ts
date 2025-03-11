import { Component, OnChanges } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MoneyPipe } from "../../pipes/money.pipe";
import { CurrencyPipe } from '@angular/common';

@Component({
  selector: 'app-currency',
  imports: [FormsModule, MoneyPipe,CurrencyPipe],
  templateUrl: './currency.component.html',
  styleUrl: './currency.component.css'
})
export class CurrencyComponent {

  amount:number=0;

  
  
  

}
