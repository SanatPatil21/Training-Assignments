import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { CircleComponent } from "../circle/circle.component";
import { CurrencyComponent } from "../currency/currency.component";
import { InvestmentsComponent } from "../investments/investments.component";

@Component({
  selector: 'app-tab-slider',
  imports: [CommonModule, CircleComponent, CurrencyComponent, InvestmentsComponent],
  templateUrl: './tab-slider.component.html',
  styleUrl: './tab-slider.component.css'
})
export class TabSliderComponent {
  selectedTab = 0;

  selectTab(index: number) {
    this.selectedTab = index;
  }
}
