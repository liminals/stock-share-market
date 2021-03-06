var totalTurns;
var timer;
var allStocksJSON;
var labelData = [];
var clientTurnJSON;	// ClientTurn object, used by both client and server
var stockBuySelect = $("#selectBuy"); // select box
var winner;

// for host
if (clientTurnJSON.type == 'HOST') {
	initialLoading();
	getBalance();
	timer = setInterval(countTurns, 1000 * 3);
// for client
} else {
	if(clientTurnJSON.type == 'CLIENT'){
		$("#playerName").text('Player : ' + clientTurnJSON.player);
		console.log('client');
		isStartTimer = setInterval(isGameStarted, 1000 * 3);
	}
}

//run by a client to check wether the game has been started by the host
var isStartTimer;


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
			clientTurnJSON = gameData;
			if (clientTurnJSON.game_status == 'STARTED') {
				clearInterval(isStartTimer);
				console.log('timer ended');
				initialLoading();
				timer = setInterval(countTurns, 1000 * 3);
			} else if (clientTurnJSON.game_status == 'YET_TO_START') {
				alert('Please wait until the host starts the game!!!');
			} else {
				alert('Game No longer hosted!');
				var url = serviceUrl + 'GameEnded';
				$.ajax(url, {
					type: 'post',
					dataType: 'json',
					success: function(data) {
						clearInterval(isStartTimer);
						window.location = data.url;
					}
				});
			}
		},
		error: function() {
			console.log('error when checking game status');
		}
	});
}


//loads the initial stock data from database, only one time
function loadInitailStocks() {
	var allStocksURL = serviceUrl + 'rest/game/' + clientTurnJSON.gameId+ '/getStock';
	$.ajax(allStocksURL, {
		type: 'post',
		success : function(data) {
			allStocksJSON = data;
			loadJSONData();
			populateSelectBox();
		},
		error : function() {
			console.log('An error occured!');
		}
	});
}

// this should executed when game starts
function initialLoading() {
	if (clientTurnJSON.type == 'CLIENT') {
		turn = clientTurnJSON.currentTurn;
		$("#playerName").text('Player : ' + clientTurnJSON.player);
		$("#totalTurns").html('Total Turns: ' + clientTurnJSON.totalTurns);
		$('#currentTurn').text('Current Turn: ' + turn);
		labelData.push(turn);
		loadInitailStocks();
	} else {
		var url = serviceUrl + 'rest/game/' + clientTurnJSON.gameId+ '/getTurn';
		$.ajax(url, {
			type: 'get',
			success: function(data) {
				clientTurnJSON.currentTurn = data;
				turn = clientTurnJSON.currentTurn;
				$("#playerName").text('Player : ' + clientTurnJSON.player);
				$("#totalTurns").html('Total Turns: ' + clientTurnJSON.totalTurns);
				$('#currentTurn').text('Current Turn: ' + turn);
				labelData.push(turn);
				loadInitailStocks();
			}
		})
	}
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
				$('#leaveGame').prop('disabled', false);
				if (winner != clientTurnJSON.player) {
					alert(winner + ' wins the game!!')
				} else {
					alert('Congradulations!!, You won the Game!');
				}
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
			$.each(newStocksData, function(k, v) {
				updateStockPrice(v);
			});
		},
		error: function(error) {
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
				checkWinner();
			}
		},
		error: function() {
			console.log('error');
		}
	})
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
			if (turn != clientTurnJSON.currentTurn){
				turn = clientTurnJSON.currentTurn;
				$('#currentTurn').text('Current Turn: ' + turn);
				labelData.push(turn);
				getBalance();
				getLatestPortfolio();
				getAllTransactions();
			}
		}
	});
}

// returns the current balance from server, and updates UI
function getBalance() {
	var url = serviceUrl + 'rest/bank/getBalance/' + clientTurnJSON.player;
	$.ajax(url, {
		type: 'get',
		success: function(account) {
			$("#currentBalance").text('Current balance: ' + account.current_balance);
		}
	});
}

// populate the select box for transactions
function populateSelectBox() {
	$.each(allStocksJSON, function(key, value) {
		stockBuySelect.append('<option value=' + value.id + '>' + value.name + '</option>');
	});
}


// get the winner
function checkWinner() {
	var url = serviceUrl + 'rest/game/' + clientTurnJSON.gameId + '/winner';
	$.ajax(url, {
		type: 'get',
		success: function(data) {
			winner = data;
			$('#currentWinner').text('Current Winner : ' + data);
		}
	});
}