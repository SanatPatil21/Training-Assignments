import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { get } from 'http';
import { CircleResultComponent } from "../circle-result/circle-result.component";

@Component({
  selector: 'app-circle',
  imports: [CommonModule, FormsModule, CircleResultComponent],
  templateUrl: './circle.component.html',
  styleUrl: './circle.component.css'
})
export class CircleComponent {

  radius:number=0;
  showResults = false;

  getDiameter(){
    return this.radius*2;
  }


  getArea(){
    return Math.PI * this.radius * this.radius;
  }

  getPerimeter(){
    return 2 * Math.PI * this.radius;
  }

  calculate(){
    this.getDiameter();
    this.getArea();
    this.getPerimeter();
    this.showResults=true;
  }




}
