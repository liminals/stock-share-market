var turn = 1;
var timer = setInterval(countTurns, 1000 * 2);
var allStocksJSON;
console.log(serviceUrl);

function countTurns() {
	if (turn == 5)
		clearInterval(timer);
	$('#currentTurn').text('Current Turn: ' + turn++);
	// updateStockPrices();
}

function updateStockPrices() {
	var allStocksURL = serviceUrl + 'rest/stock/all';
	$.ajax(allStocksURL, {
		dataType : 'json',
		success : function(data) {
			allStocksJSON = data;
			updateStockDash(data);
			loadJSONData();
		},
		error : function() {
			console.log('An error occured!');
		}
	});
}

function updateStockDash(data) {
	var result = '<ul>';
	$.each(data, function(key, value) {
		result += '<li>';
		result += '<h5>' + value.name + ' [' + value.short_name + ']' + '</h5>';
		result += '<p>' + value.current_price + '</p>';
		result += '</li>';
	});
	result += '</ul>';
	$('#stockGraph').html(result);
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