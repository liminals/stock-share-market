var turn;
var totalTurns;
var timer;
var allStocksJSON;
var labelData = [];
var clientTurnJSON;	// ClientTurn object, used by both client and server

// this if for game host only
if (clientTurnJSON.currentTurn > 0) {
	 timer = setInterval(countTurns, 1000 * 3);
}
// this if for executed when visiting page
if (clientTurnJSON.currentTurn > 0) {
	// for host
	initialLoading();
} else {
	// for client
	alert('Please wait until the host starts the game!!!');
}


//loads the initial stock data from database, only one time
function loadInitailStocks() {
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

// this should executed when game starts
function initialLoading() {
	turn = clientTurnJSON.currentTurn;
	$('#currentTurn').text('Current Turn: ' + turn);
	labelData.push(turn);
	loadInitailStocks();
};

// update client on each turn
function countTurns() {
	if (turn == clientTurnJSON.totalTurns)
		clearInterval(timer);
	if (clientTurnJSON.currentTurn > 0)
		canRequestData();
}

$('#searchStock').on('input propertychange paste', function() {
	var searchValue = $.trim($(this).val());
	var reg = new RegExp(searchValue, 'i');
	if (searchValue != '') {
		var result = '<ul>';
		$.each(allStocksJSON, function(key, value) {
			if(value.name.match(reg)) {
				result += '<li>';
				result += '<h4>' + value.name + '</h4>';
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
	var gameid = clientTurnJSON.gameId;
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
	var eventUrl = serviceUrl + 'rest/game/' +  clientTurnJSON.gameId + '/event';
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
	var url = serviceUrl + 'rest/game/' + clientTurnJSON.gameId + '/turn/' + clientTurnJSON.currentTurn;
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


function isGameStarted(){
	var url = serviceUrl + 'rest/game/isStarted';
	var gameData = JSON.stringify(clientTurnJSON);
	$.ajax(url, {
		type: 'post',
		dataType: 'json',
		data: gameData,
		contentType: 'application/json',
		success: function(gameData) {
			console.log(gameData);
			clientTurnJSON = gameData;
			if (clientTurnJSON.game_status == 'STARTED') {
				clearInterval(isStartTimer);
				console.log('timer ended');
				initialLoading();
				timer = setInterval(countTurns, 1000 * 3);
			}
		},
		error: function() {
			console.log('error when checking game status');
		}
	});
}
var isStartTimer;
if(clientTurnJSON.type == 'CLIENT'){
	console.log('client');
	isStartTimer = setInterval(isGameStarted, 1000 * 5);
}

function updateTurn() {
	var turnUrl = serviceUrl + 'rest/game/' + clientTurnJSON.gameId +'/updateTurn';
	var gameData = JSON.stringify(clientTurnJSON);
	$.ajax(turnUrl, {
		type: 'post',
		dataType: 'json',
		data: gameData,
		contentType: 'application/json',
		success: function(gameData) {
			clientTurnJSON = gameData;
			console.log(gameData);
			if (turn != clientTurnJSON.currentTurn){
				turn = clientTurnJSON.currentTurn;
				$('#currentTurn').text('Current Turn: ' + turn);
				labelData.push(turn);
			}
		}
	});
}