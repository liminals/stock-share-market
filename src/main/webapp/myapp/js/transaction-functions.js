
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
	console.log(data);
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