import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-mutual-fund',
  imports: [CommonModule,FormsModule],
  templateUrl: './mutual-fund.component.html',
  styleUrl: './mutual-fund.component.css'
})
export class MutualFundComponent {
  monthlySIP: number = 0;
  duration: number = 0;
  roi: number = 0;

  calculateTotal(): number {
    if (this.duration <= 0 || this.monthlySIP <= 0) {
      return 0;
    }
    
    if(this.roi < -25 || this.roi > 25) {
      return 0;
    }

    const months = this.duration * 12;
    const interestRate = this.roi / 100;
    return this.monthlySIP * months * (1 + interestRate);
  }

}
