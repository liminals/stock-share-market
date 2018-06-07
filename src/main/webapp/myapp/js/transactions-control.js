/*allStocksJSON;
var stockBuySelect = $("#selectBuy");
clientTurnJSON*/
var portfolioJson;
var buttonBuy = $("#buttonBuy");
var buttonSell = $('#buttonSell');
var stockname = $("#stockname");
var stockprice = $("#stockprice");
var portfolioDiv = $('#portfolioDiv');
var stockSellSelect = $('#selectSell');

var sellStockdetails = $('#sellStockDetails');
var sellTransactionFields = $('#sellTransactionFields');
var buyStockdetails = $('#buyStockDetails');
var buyTransactionFields = $('#buyTransactionFields');

var portfolioname = $('#portfolioname');
var portfoliovalue = $('#portfoliovalue');
var portfolioqty = $('#portfolioqty');

sellTransactionFields.css('display', 'none');
buyTransactionFields.css('display', 'none');
buyStockdetails.css('display', 'none');
sellStockdetails.css('display', 'none');

buttonBuy.prop('disabled', true);
buttonSell.prop('disabled', true);

function BrokerTransaction(turn, type, stock, qty, price) {
	this.turn = turn;
	this.type = type;
	this.stock = stock;
	this.qty = qty;
	this.price = price;
}


function clearFields() {
	stockname.text('');
	stockprice.text('');
	portfolioname.text('');
	portfoliovalue.text('');
	portfolioqty.text('');
	$('#transactionValue').val('');
	$('#transactionQty').val('');
	$('#sellQty').val('');
}


// populate the stock details
// below in the UI
stockBuySelect.change(function() {
	clearFields();
	buttonBuy.prop('disabled', true);
	buttonSell.prop('disabled', true);
	updateStockDataForBuy(this.value);
	
	sellTransactionFields.css('display', 'none');
	sellStockdetails.css('display', 'none');
	buyTransactionFields.css('display', 'block');
	buyStockdetails.css('display', 'block');
});

stockSellSelect.change(function() {
	var portname = $(this).find("option:selected").text();
	var value = JSON.parse(this.value);
	clearFields();
	buttonBuy.prop('disabled', true);
	buttonSell.prop('disabled', true);
	
	sellTransactionFields.css('display', 'block');
	sellStockdetails.css('display', 'block');
	buyTransactionFields.css('display', 'none');
	buyStockdetails.css('display', 'none');
	
	updateStockDataForSell(value);
});

// update the stock details on the #stockDetails Div for buying
function updateStockDataForBuy(id) {
	$.each(allStocksJSON, function(k, v){
		if (v.id == id) {
			stockname.text(v.name);
			stockprice.text(v.current_price);
		}
	});
}

// update the stock details on the #stockDetails Div for selling
function updateStockDataForSell(value) {
	portfolioname.text(value.name);
	portfoliovalue.text(value.value);
	portfolioqty.text(value.qty);
}

$('#transactionQty').on('input propertychange paste', function() {
	var qty = $.trim($(this).val());
	if (qty != '' && parseInt(qty) > 0) {
		buttonBuy.prop('disabled', false);
		$('#transactionValue').val(parseInt(qty) * parseFloat(stockprice.text()));
	} else {
		buttonBuy.prop('disabled', true);
		$('#transactionValue').val('');
	}
});

$('#sellQty').on('input propertychange paste', function() {
	var qty = $.trim($(this).val());
	if (qty != '' && parseInt(qty) > 0) {
		buttonSell.prop('disabled', false);
		$('#transactionValue').val(parseInt(qty) * parseFloat(stockprice.text()));
	} else {
		buttonSell.prop('disabled', true);
		$('#transactionValue').val('');
	}
});

buttonBuy.on('click', function(){
	var turn = clientTurnJSON.currentTurn;
	var gameid = clientTurnJSON.gameId;
	var player = clientTurnJSON.player;
	var stock = stockname.text();
	var qty = $('#transactionQty').val();
	var price = parseFloat(stockprice.text());
	
	var reqData = new BrokerTransaction(turn, "BUY", stock, qty, price);
	var url = serviceUrl + 'rest/broker/buy/' + gameid + '/' + player;
	
	var reqJson = JSON.stringify(reqData);
	
	$.ajax(url, {
		type: 'post',
		dataType: 'json',
		data: reqJson,
		contentType: 'application/json',
		success: function(data) {
			clearFields();
			if (data.status == 'INSUFFICIENT_FUNDS') {
				alert('Transaction Failed!. Insufficient Funds!');
			} else if (data.status == 'PRICE_DO_NOT_MATCH') {
				alert('Transaction Failed!. Price does not match!');
			} else {
				// update transaction info
				updateLatestTransactionUI(data);
				getLatestPortfolio();
				getBalance();
				getAllTransactions();
			}
		}
	});
});


buttonSell.on('click', function() {
	var sellingprice;
	//get price of selling stock
	function getSellStockPrice(stock) {
		$.each(allStocksJSON, function(k, v){
			if(v.name == stock) {
				sellingprice =  v.current_price;
			}
		});
	}
	
	var turn = clientTurnJSON.currentTurn;
	var gameid = clientTurnJSON.gameId;
	var player = clientTurnJSON.player;
	var stock = portfolioname.text();
	var qty = $('#sellQty').val();
	
	// var price = getSellStockPrice(stock);
	getSellStockPrice(stock);
	var price = sellingprice;
		
	var reqData = new BrokerTransaction(turn, "SELL", stock, qty, price);
	var url = serviceUrl + 'rest/broker/sell/' + gameid + '/' + player;
	
	var reqJson = JSON.stringify(reqData);
	
	$.ajax(url, {
		type: 'post',
		dataType: 'json',
		data: reqJson,
		contentType: 'application/json',
		success: function(data) {
			clearFields();
			if (data.status == 'PRICE_DO_NOT_MATCH') {
				alert('Transaction Failed!. Price doesn' + "'" + 't match.')
			} else if (data.status == 'INSUFFICIENT_STOCKS') {
				alert('Transaction Failed!. You don' + + "'" + 't have specified stock quantity!');
			} else {
				// update transaction info
				updateLatestTransactionUI(data);
				getLatestPortfolio();
				getBalance();
				getAllTransactions();
			}
		}
	});
});


// updates the last transaction in the UI
function updateLatestTransactionUI(data) {
	var trnsValue = data.qty * parseFloat(data.price);
	var html = '<p>';
	
	if (data.type == 'BUY') {
		html += 'Bought ';
	} else {
		html += 'Sold ';
	}
	html += data.qty + ' of ';
	html += data.stock + ' stocks at ';
	html += data.price + ' for ';
	html += trnsValue + ' !';
	$('#latestTransaction').html(html);
}

// portfolio related UI
// get the portfolio from backend
// assign it to the portfolioJson and updates the portfolio div
function getLatestPortfolio() {
	var player = clientTurnJSON.player;
	var url = serviceUrl + 'rest/broker/portfolio/get/' + player;
	$.ajax(url, {
		type: 'get',
		success: function(data) {
			portfolioJson = data;
			updatePortfolioinUI(portfolioJson);
			populateSellSelect(portfolioJson);
		}
	});
}

// update portfolio in the UI
function updatePortfolioinUI(data) {
	var html = '<ul> Portfolio';
	$.each(data, function(k, v) {
		html += '<li>';
		html +=	'<ul>' + v.name;
		html += '<li>Qty owned: ' + v.qty + '</li>';
		html += '<li>Worth    : ' + v.value + '</li>';
		html += '</ul>';
		html += '</li>';
	});
	html += '</ul>';
	portfolioDiv.html(html);
}


// sell stocks related
function populateSellSelect(data) {
	stockSellSelect.empty();
	$.each(data, function(key, value) {
		var j = JSON.stringify(value);
	    stockSellSelect.append($("<option/>", {
	        value: j,
	        text: value.name
	    }));
	});
}

////////////// transaction related
// get all the transactions from the backend
function getAllTransactions() {
	var player = clientTurnJSON.player;
	var url = serviceUrl + 'rest/broker/transactions/' + player + '/all';
	console.log(url);
	
	$.ajax(url, {
		type: 'get',
		success: function(data) {
			console.log(data);
			updateTransactionsInUI(data);
		},
		error: function() {
			console.log('error when calling transactions');
		}
	});
}

function updateTransactionsInUI(data) {
	var html = '<ul> Transactions History';
	$.each(data, function(k, v){
		html += '<li>';
		if (v.type == 'BUY') {
			html += 'Bought ';
		} else {
			html += 'Sold ';
		}
		html += v.qty + ' of ';
		html += v.stock + ' stocks at ';
		html += v.price + ' for ';
		var value = parseFloat(v.price) * v.qty;
		html += value + ' !';
		html += '</li>';
	});
	html += '</ul>';
	$("#transactionHistory").html(html);
}