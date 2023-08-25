import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {

  private _discrepancyValue: number;

  constructor() { }

  public set discrepancyValue(value:number){
    this._discrepancyValue=value;
  }

  public get discrepancyValue(){
    return this._discrepancyValue;
  }

}