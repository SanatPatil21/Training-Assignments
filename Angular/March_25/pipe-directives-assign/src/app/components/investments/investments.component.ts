import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { LoanComponent } from "../loan/loan.component";
import { DepositComponent } from "../deposit/deposit.component";
import { MutualFundComponent } from "../mutual-fund/mutual-fund.component";

@Component({
  selector: 'app-investments',
  imports: [FormsModule, CommonModule, LoanComponent, DepositComponent, MutualFundComponent],
  templateUrl: './investments.component.html',
  styleUrl: './investments.component.css'
})
export class InvestmentsComponent {
  investmentOptions = ['LOAN', 'DEPOSIT', 'MUTUAL FUND'];
  selectedInvestment: string = '';

}
