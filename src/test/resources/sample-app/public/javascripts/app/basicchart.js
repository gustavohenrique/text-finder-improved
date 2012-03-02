function get_bar_chart(container, categories, series, formatter_symbol, export_filename) {
	
	return new Highcharts.Chart({
	      chart: {
	         renderTo: container,
	         defaultSeriesType: 'bar',
	         marginRight: 130,
	         marginBottom: 25
	      },
	      title: {
	         text: '',
	         x: -20 //center
	      },
	      xAxis: {
	         categories: categories
	      },
	      yAxis: {
	         title: {
	            text: ''
	         },
	         plotLines: [{
	            value: 0,
	            width: 1,
	            color: '#808080'
	         }]
	      },
	      plotOptions: {
	          series: {
	              dataLabels: {
	                  enabled: true,
	                  align: 'right',
	                  color: '#FFFFFF',
	                  x: -10
	              },
	              pointPadding: 0.2,
	              groupPadding: 0
	          }
	      },
	      tooltip: {
	         formatter: function() {
	            return '<b>'+ this.series.name +'</b><br/>'+
	            this.x +': '+ this.y+formatter_symbol;
	         }
	      },
	      legend: {
	         layout: 'vertical',
	         align: 'right',
	         verticalAlign: 'top',
	         x: -10,
	         y: 100,
	         borderWidth: 0
	      },
	      credits: {
		      enabled: false
		  },
		  exporting: {
		      enabled: true,
		      filename: export_filename
		  },
		  navigation: {
		      buttonOptions: {
		          borderRadius: 0
		      }
		  },
	      series: series
	   });
}