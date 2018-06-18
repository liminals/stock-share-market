var ctx = $('#stockGraph');
var labelData = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'];
var stockData = []; // individual stock

function getRandomColor() {
	var letters = '0123456789ABCDEF';
	var color = '#';
	for (var i = 0; i < 6; i++) {	
		color += letters[Math.floor(Math.random() * 16)];
	}
	return color;
}

function Stock(name, short_name) {
	this.label = name + '[' + short_name + ']';
	this.data = [];
	this.fill = false;
	this.borderColor = getRandomColor();
}

function loadJSONData() {
	var stock;
	$.each(allStocksJSON, function(key, value) {
		if (stockData.length == 0 || !containsInArray(stockData, value.name)) {
			stock = new Stock(value.name, value.short_name);
		}
		$.each(allStocksJSON, function(k, v) {
			if (v.name === value.name)
				stock.data.push(v.current_price);
		});
		stockData.push(stock);
	});
	loadGraph();
}

var data = {
		labels: labelData,
	    datasets: stockData,
}

function loadGraph() {
	var options = {
			scales: {
				yAxes: [{
					ticks: {
						beginAtZero:true
			        },
			        scaleLabel: {
			        	display: true,
			        	labelString: 'Stock',
			        	fontSize: 20 
			        }
				}]            
			}  
	};
	var myChart = new Chart(ctx, {
	    type: 'line',
	    data: data,
	    options: options
	});
}

function containsInArray(stockData) {
	for(var i = 0; i < stockData.length; i++) {
		if (stockData[i].name == 'Magenic') {
			return true;
		}
	}
	return false;
}