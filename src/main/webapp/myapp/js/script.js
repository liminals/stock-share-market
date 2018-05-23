var turn = 1;
var timer = setInterval(countTurns, 1000 * 2);
var allStocksJSON;
console.log(serviceUrl);

function countTurns() {
	if (turn == 5)
		clearInterval(timer);
	$('#currentTurn').text('Current Turn: ' + turn++);
	// loadInitialStocks();
}

// loads the initial stock data from database, only one time
function loadInitialStocks() {
	var allStocksURL = serviceUrl + 'rest/stock/all';
	$.ajax(allStocksURL, {
		dataType : 'json',
		success : function(data) {
			allStocksJSON = data;
			loadJSONData();
		},
		error : function() {
			console.log('An error occured!');
		}
	});
	var gameURL = serviceUrl + 'rest/game/start';
	$.ajax(gameURL, {
		type: 'post',
		success : function(data) {
			console.log(data);
		},
		error : function() {
			console.log('An error occured!');
		}
	});
}

$('#searchStock').on('input propertychange paste', function() {
	var searchValue = $.trim($(this).val());
	var reg = new RegExp(searchValue, 'i');
	if (searchValue != '') {
		var result = '<ul>';
		$.each(allStocksJSON, function(key, value) {
			if(value.name.match(reg)) {
				result += '<li>';
				result += '<h4>' + value.name + ' [' + value.short_name + ']' + '</h4>';
				result += '<p>' + value.current_price + '</p>';
				result += '</li>';
			}
		});
		result += '</ul>';
		$('#searchResults').html(result);
	} else {
		$('#searchResults').html("");
	}
});

// update the price of a single stock
function updateNewStockPrices() {
	// var updatePriceStock = serviceUrl + 'rest/stock/updateprice/';
	var updatePriceStock = serviceUrl + 'rest/game/getNewPrice';
	$.each(allStocksJSON, function(k, v) {
		var data = JSON.stringify(v);
		console.log(data);
		$.ajax(updatePriceStock, {
			type: 'post',
			data: data,
			dataType : 'json',
			contentType: 'application/json',
			success : function(newStockData) {
				updateStockPrice(newStockData);
			},
			error : function() {
				console.log('An error occured!');
			}
		});
	});
}