import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'money'
})
export class MoneyPipe implements PipeTransform {

  transform(value: number, ...args: unknown[]): number {
    switch (args[0]) {
      case 'USD':
        return value * 0.012;
      case 'JPY':
        return value * 1.70;
      case 'SAR':
        return value * 0.043;
      case 'EUR':
        return value * 0.011;
      case 'AUD':
        return value * 0.018;
      default:
        return value;
    }
  }

}
