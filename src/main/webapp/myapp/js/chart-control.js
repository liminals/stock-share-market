var ctx = $('#stockGraph');
var myChart;
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
	this.label = name;
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
	initGraph();
}

function updateStockPrice(newStockData) {
	// set the current price in allStocksJSON
	for (var i = 0; i < allStocksJSON.length; i++) {
		if (allStocksJSON[i].name === newStockData.name) {
			allStocksJSON[i].current_price = newStockData.current_price;
			break;
		}
	}
	
	// load the new price into stockData for graph
	for (var i = 0; i < stockData.length; i++) {
		if (stockData[i].label === newStockData.name) {
			stockData[i].data.push(newStockData.current_price);
			break;
		}
	}
	myChart.update();
}

var data = {
		labels: labelData,
	    datasets: stockData,
}

function initGraph() {
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
	myChart = new Chart(ctx, {
	    type: 'line',
	    data: data,
	    options: options
	});
}

function containsInArray(stockData, name) {
	for(var i = 0; i < stockData.length; i++) {
		if (stockData[i].name == name) {
			return true;
		}
	}
	return false;
}