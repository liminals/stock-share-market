var turn;
var totalTurns;
var timer;
var allStocksJSON;
var labelData = [];
var clientTurnJSON;	// ClientTurn object, used by both client and server

// this if for game host only
if (clientTurnJSON.currentTurn > 0) {
	 timer = setInterval(countTurns, 1000 * 4);
}
// this if for executed when visiting page
if (clientTurnJSON.currentTurn > 0) {
	// for host
	getBalance();
	initialLoading();
	$("#totalTurns").html('Total Turns: ' + clientTurnJSON.totalTurns);
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
	$("#playerName").text('Player : ' + clientTurnJSON.player);
	$('#currentTurn').text('Current Turn: ' + turn);
	labelData.push(turn);
	loadInitailStocks();
};

// update client on each turn
function countTurns() {
	var url = serviceUrl + 'rest/game/' + clientTurnJSON.gameId + '/isEnded';
	$.ajax(url, {
		type: 'get',
		success: function(data) {
			if (data == 'true') {
				clearInterval(timer);
				alert('game ended');
			} else {
				canRequestData();
			}
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
// updates the graph
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
			console.log(newStocksData);
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

// this makes requests when only turn is updated in server
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

//run by a client to check wether the game has been started by the host
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

// run by a client to check wether the game has been started by the host
var isStartTimer;
if(clientTurnJSON.type == 'CLIENT'){
	$("#playerName").text('Player : ' + clientTurnJSON.player);
	console.log('client');
	isStartTimer = setInterval(isGameStarted, 1000 * 3);
}

// both client and host
// executes each time when turn updates
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
				getBalance();
			}
		}
	});
}

function getBalance() {
	var url = serviceUrl + 'rest/bank/getBalance/' + clientTurnJSON.player;
	$.ajax(url, {
		type: 'get',
		success: function(account) {
			updateBalance(account);
		}
	});
}

function updateBalance(account) {
	$("#currentBalance").text('Current balance: ' + account.current_balance);
}