var turn;
var totalTurns;
var timer = setInterval(countTurns, 1000 * 3);
var allStocksJSON;
var labelData = [];
var gameJSON;

(function initialLoading() {
	var gameUrl = serviceUrl + 'rest/game/start/30'; 
	$.ajax(gameUrl, {
		type: 'post',
		success: function(gameData) {
			gameJSON = gameData;
			$('#currentTurn').text('Current Turn: ' + gameJSON.currentTurn);
			labelData.push(gameJSON.currentTurn);
			loadInitialStocks();
		}
	});
})();

function countTurns() {
	if (turn == gameJSON.totalTurns)
		clearInterval(timer);
	canRequestData();
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
// need to be automatic delete this
function updateNewStockPrices() {
	var gameid = gameJSON.gameId;
	var updatePriceStock = serviceUrl + 'rest/game/' + gameid + '/getNewPrice';
	var stocksJSON = JSON.stringify(allStocksJSON);
	$.ajax(updatePriceStock, {
		type: 'post',
		data: stocksJSON,
		dataType: 'json',
		contentType: 'application/json',
		success: function(newStocksData) {
			$.each(newStocksData, function(k, v) {
				updateStockPrice(v);
				//console.log(v);
			});
		},
		error: function(error) {
			console.log(error);
			console.log('An error occured!');
		}
	});
	checkForEvents();
}

// check for events
function checkForEvents() {
	var eventUrl = serviceUrl + 'rest/game/' +  gameJSON.gameId + '/event';
	$.ajax(eventUrl, {
		type: 'get',
		dataType: 'json',
		success: function(data) {
			if (data !== undefined) {
				var ul = '<ul>' + data.name;
				ul += '<li> value:    ' + data.value + '</li>';
				ul += '<li> duration: ' + data.duration + '</li>';
				ul += '</ul>';
				$('#eventDetails').html(ul);
				console.log(data);
			} else {
				$('#eventDetails').html('');
			}
		},
		error: function() {
			console.log('error');
		}
	});
}

function canRequestData() {
	var url = serviceUrl + 'rest/game/' + gameJSON.gameId + '/turn/' + gameJSON.currentTurn;
	$.ajax(url, {
		type: 'get',
		dataType: 'json',
		success: function(data) {
			if (data) {
				updateTurn();
				updateNewStockPrices();
			}
			console.log(data);
		},
		error: function() {
			console.log('error');
		}
	})
}

function updateTurn() {
	var turnUrl = serviceUrl + 'rest/game/' + gameJSON.gameId +'/updateTurn';
	var gameData = JSON.stringify(gameJSON);
	$.ajax(turnUrl, {
		type: 'post',
		dataType: 'json',
		data: gameData,
		contentType: 'application/json',
		success: function(gameData) {
			gameJSON = gameData;
			console.log(gameData);
			if (turn != gameJSON.currentTurn){
				turn = gameJSON.currentTurn;
				$('#currentTurn').text('Current Turn: ' + turn);
				labelData.push(turn);
			}
		}
	});
}