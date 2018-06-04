/*allStocksJSON;
var stockSelect = $("#stocks");
clientTurnJSON*/
var buttonBuy = $("#buttonBuy");
var buttonSell = $('#buttonSell');
var stockname = $("#stockname");
var stockprice = $("#stockprice");
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
	$('#transactionValue').val('');
	$('#transactionQty').val('');
}


// populate the stock details
// below in the UI
stockSelect.change(function() {
	console.log(this.value);
	clearFields();
	buttonBuy.prop('disabled', true);
	buttonSell.prop('disabled', true);
	updateStockDataForSelect(this.value);
});

// update the stock details on the #stockDetails Div
function updateStockDataForSelect(id) {
	$.each(allStocksJSON, function(k, v){
		if (v.id == id) {
			stockname.text(v.name);
			stockprice.text(v.current_price);
		}
	});
}

$('#transactionQty').on('input propertychange paste', function() {
	var qty = $.trim($(this).val());
	console.log(qty);
	if (qty != '' && parseInt(qty) > 0) {
		buttonSell.prop('disabled', false);
		buttonBuy.prop('disabled', false);
		$('#transactionValue').val(parseInt(qty) * parseFloat(stockprice.text()));
	} else {
		buttonSell.prop('disabled', true);
		buttonBuy.prop('disabled', true);
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
	console.log(reqData);
	
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
				alert('Transaction Failed!. Price doesn' + "'" + 't match!');
			} else {
				// update transaction info
				updateLatestTransactionUI(data);
			}
		}
	});
});

buttonSell.on('click', function() {
	var turn = clientTurnJSON.currentTurn;
	var gameid = clientTurnJSON.gameId;
	var player = clientTurnJSON.player;
	var stock = stockname.text();
	var qty = $('#transactionQty').val();
	var price = parseFloat(stockprice.text());
	
	var reqData = new BrokerTransaction(turn, "SELL", stock, qty, price);
	var url = serviceUrl + 'rest/broker/sell/' + gameid + '/' + player;
	
	var reqJson = JSON.stringify(reqData);
	console.log(reqData);
	
	$.ajax(url, {
		type: 'post',
		dataType: 'json',
		data: reqJson,
		contentType: 'application/json',
		success: function(data) {
			clearFields();
			console.log(data);
			if (data.status == 'PRICE_DO_NOT_MATCH') {
				alert('Transaction Failed!. Price doesn' + "'" + 't match.')
			} else {
				// update transaction info
				updateLatestTransactionUI(data);
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