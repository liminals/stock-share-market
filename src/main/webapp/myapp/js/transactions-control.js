/*allStocksJSON;
var stockSelect = $("#stocks");
clientTurnJSON*/
var buttonBuy = $("#buttonBuy");
var buttonSell = $('#buttonSell');
var stockname = $("#stockname");
var stockprice = $("#stockprice");
buttonBuy.prop('disabled', true);
buttonSell.prop('disabled', true);

function BrokerTransaction(name, stock, qty, price) {
	this.name = name;
	this.stock = stock;
	this.qty = qty;
	this.price = price;
}


// populate the stock details
// below in the UI
stockSelect.change(function() {
	console.log(this.value);
	$('#transactionValue').val('');
	$('#transactionQty').val('');
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
	var player = clientTurnJSON.player;
	var stock = stockname.text();
	var qty = $('#transactionQty').val();
	var price = parseFloat(stockprice.text());
	
	var reqData = new BrokerTransaction(name, stock, qty, price);
	var url = serviceUrl + 'rest/broker/buy/' + player;
	
	var reqJson = JSON.stringify(reqData);
	console.log(reqData);
	
	$.ajax(url, {
		type: 'post',
		dataType: 'json',
		data: reqJson,
		contentType: 'application/json',
		success: function(data) {
			console.log(data);
			if (data.status == 'INSUFFICIENT_FUNDS') {
				alert('Transaction Failed!. Insufficient funds.')
			} else {
				// update latest transaction in UI
			}
		}
	});
});

buttonSell.on('click', function(){
	var turn = clientTurnJSON.currentTurn;
	var player = clientTurnJSON.player;
	var stock = stockname.text();
	var qty = $('#transactionQty').val();
	var price = parseFloat(stockprice.text());
	
	var reqData = new BrokerTransaction(name, stock, qty, price);
	var url = serviceUrl + 'rest/broker/sell/' + player;
	
	var reqJson = JSON.stringify(reqData);
	console.log(reqData);
	
	$.ajax(url, {
		type: 'post',
		dataType: 'json',
		data: reqJson,
		contentType: 'application/json',
		success: function(data) {
			console.log(data);
			// update latest transaction in UI
		}
	});
});