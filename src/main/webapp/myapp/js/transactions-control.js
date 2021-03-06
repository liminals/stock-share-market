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
	var reg = new RegExp('^[0-9]+$');
	if (qty != '' && parseInt(qty) > 0 && reg.test(qty)) {
		buttonBuy.prop('disabled', false);
		$('#transactionValue').val(parseInt(qty) * parseFloat(stockprice.text()));
	} else {
		buttonBuy.prop('disabled', true);
		$('#transactionValue').val('');
	}
});

$('#sellQty').on('input propertychange paste', function() {
	var qty = $.trim($(this).val());
	var reg = new RegExp('^[0-9]+$');
	if (qty != '' && parseInt(qty) > 0 && reg.test(qty)) {
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
	buttonBuy.prop('disabled', true);
	buyTransactionFields.css('display', 'none');
	buyStockdetails.css('display', 'none');
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
				sellTransactionFields.css('display', 'none');
			} else if (data.status == 'INSUFFICIENT_STOCKS') {
				alert('Transaction Failed!. You do not have specified stock quantity!');
			} else {
				// update transaction info
				updateLatestTransactionUI(data);
				getLatestPortfolio();
				getBalance();
				getAllTransactions();
			}
		}
	});
	buttonSell.prop('disabled', true);
	sellTransactionFields.css('display', 'none');
	sellStockdetails.css('display', 'none');
});


// updates the last transaction in the UI
function updateLatestTransactionUI(data) {
	var trnsValue = data.qty * parseFloat(data.price);
	var html = '<h5>Latest Transaction</h5>';
	html += '<div class="alert alert-primary" role="alert">';
	
	if (data.type == 'BUY') {
		html += 'Bought ';
	} else {
		html += 'Sold ';
	}
	html += data.qty + ' of ';
	html += data.stock + ' stocks at ';
	html += data.price + ' for ';
	html += trnsValue + ' !';
	html += '</div>'
	$('#latestTransaction').html(html);
}