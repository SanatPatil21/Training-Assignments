import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-circle-result',
  imports: [CommonModule],
  templateUrl: './circle-result.component.html',
  styleUrl: './circle-result.component.css'
})
export class CircleResultComponent {

  @Input('diameter')
  diameter!:number;
  
  @Input('area')
  area!:number;

  @Input('perimeter')
  perimeter!:number;


}
