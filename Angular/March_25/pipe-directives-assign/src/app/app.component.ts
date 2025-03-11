import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TabSliderComponent } from "./components/tab-slider/tab-slider.component";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, TabSliderComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'pipe-directives-assign';
}
