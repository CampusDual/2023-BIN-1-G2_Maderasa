import { Component, OnInit } from "@angular/core";
import {
  LineChartConfiguration,
  DataAdapterUtils,
  DiscreteBarChartConfiguration,
  ChartMarginConfiguration,
} from "ontimize-web-ngx-charts";
import { FilterExpressionUtils, Expression } from "ontimize-web-ngx";
import { D3LocaleService } from "src/app/shared/d3-locale/d3Locale.service";

@Component({
  selector: "app-resume-delivery-view",
  templateUrl: "./resume-delivery-view.component.html",
  styleUrls: ["./resume-delivery-view.component.css"],
})
export class ResumeDeliveryViewComponent implements OnInit {
  public movementTypesChartParams: LineChartConfiguration;
  public movementTypesChartParamsBalance: DiscreteBarChartConfiguration;

  public dataChartTraffic: any = [];
  public dataChartBalance: any = [];

  constructor(protected d3LocaleService: D3LocaleService) {
    const d3Locale = this.d3LocaleService.getD3LocaleConfiguration();
    this._configureLineChart(d3Locale);
  }
  

  ngOnInit() {}

  private _configureLineChart(locale: any): void {
    this.movementTypesChartParams = new LineChartConfiguration();
    this.movementTypesChartParams.noDataMessage = "NO_DATA_FOUND";
    this.movementTypesChartParams.legendPosition = "bottom";
    this.movementTypesChartParams.legend.maxKeyLength = 23;
    this.createBarChartParameters()
  }

  createBarChartParameters() {

    this.movementTypesChartParamsBalance = new DiscreteBarChartConfiguration();
    this.movementTypesChartParamsBalance.noDataMessage = "NO_DATA_FOUND";
    this.movementTypesChartParamsBalance.legend.maxKeyLength = 23;
    this.movementTypesChartParamsBalance.rightAlingYAxis = true;
    this.movementTypesChartParamsBalance.rotateLabels = 270;
    this.movementTypesChartParamsBalance.height = 600;
    this.movementTypesChartParamsBalance.color = ["#1464a5"]

  }

  createFilterTraffic(values: Array<{ attr; value }>): Expression {
    let filters: Array<Expression> = [];
    values.forEach((fil) => {
      if (fil.value) {
        if (fil.attr === "STARTDATE_I") {
          filters.push(
            FilterExpressionUtils.buildExpressionMoreEqual(
              "date_bigint",
              fil.value
            )
          );
        }
        if (fil.attr === "ENDDATE_I") {
          let d = new Date(fil.value);
          d.setHours(23);
          d.setMinutes(59);
          d.setSeconds(59);
          filters.push(
            FilterExpressionUtils.buildExpressionLessEqual(
              "date_bigint",
              Number(d)
            )
          );
        }
      }
    });

    if (filters.length > 0) {
      return filters.reduce((exp1, exp2) =>
        FilterExpressionUtils.buildComplexExpression(
          exp1,
          exp2,
          FilterExpressionUtils.OP_AND
        )
      );
    } else {
      return null;
    }
  }

  createFilterBalance(values: Array<{ attr; value }>): Expression {
    let filters: Array<Expression> = [];
    values.forEach((fil) => {
      if (fil.value) {
        if (fil.attr === "STARTDATE_I") {
          filters.push(
            FilterExpressionUtils.buildExpressionMoreEqual(
              "day_date",
              fil.value
            )
          );
        }
        if (fil.attr === "ENDDATE_I") {
          let d = new Date(fil.value);
          d.setHours(23);
          d.setMinutes(59);
          d.setSeconds(59);
          filters.push(
            FilterExpressionUtils.buildExpressionLessEqual(
              "day_date",
              Number(d)
            )
          );
        }
      }
    });

    if (filters.length > 0) {
      return filters.reduce((exp1, exp2) =>
        FilterExpressionUtils.buildComplexExpression(
          exp1,
          exp2,
          FilterExpressionUtils.OP_AND
        )
      );
    } else {
      return null;
    }
  }

  loadDataTraffic(data: Array<any>) {
    const adapter = DataAdapterUtils.createDataAdapter(
      this.movementTypesChartParams
    );
    this.dataChartTraffic = adapter.adaptResult(data);
  }

  loadDataBalance(data: Array<any>) {
    // console.log(data)
    // const colors = data.map((d) => (d.acumulated >= 0 ? "blue" : "red"));
    // this.createBarChartParameters()
    // this.colors= colors;
    // this.movementTypesChartParamsBalance.color = colors;

    const adapter = DataAdapterUtils.createDataAdapter(
      this.movementTypesChartParamsBalance
    );
          
    this.dataChartBalance = adapter.adaptResult(data);
        
    // console.log(colors)
    // console.log(this.movementTypesChartParamsBalance.color)
  }
}
