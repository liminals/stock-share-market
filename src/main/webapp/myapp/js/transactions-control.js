/*allStocksJSON;
var stockSelect = $("#stocks");
clientTurnJSON*/
var buttonBuy = $("#buttonBuy");
var stockname = $("#stockname");
var stockprice = $("#stockprice");
buttonBuy.prop('disabled', true);

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
	$('#buyValue').val('');
	$('#buyQty').val('');
	buttonBuy.prop('disabled', true);
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

$('#buyQty').on('input propertychange paste', function() {
	var qty = $.trim($(this).val());
	console.log(qty);
	if (qty != '' && parseInt(qty) > 0) {
		buttonBuy.prop('disabled', false);
		$('#buyValue').val(parseInt(qty) * parseFloat(stockprice.text()));
	} else {
		buttonBuy.prop('disabled', true);
		$('#buyValue').val('');
	}
});

buttonBuy.on('click', function(){
	var turn = clientTurnJSON.currentTurn;
	var player = clientTurnJSON.player;
	var stock = stockname.text();
	var qty = $('#buyQty').val();
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
		}
	});
});