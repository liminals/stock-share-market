// portfolio related UI
// get the portfolio from backend
// assign it to the portfolioJson and updates the portfolio div
function getLatestPortfolio() {
	var player = clientTurnJSON.player;
	var url = serviceUrl + 'rest/broker/portfolio/get/' + player;
	$.ajax(url, {
		type : 'get',
		success : function(data) {
			portfolioJson = data;
			updatePortfolioinUI(portfolioJson);
			populateSellSelect(portfolioJson);
		}
	});
}

// update portfolio in the UI
function updatePortfolioinUI(data) {
	var html = '<h4>Portfolio</h4>';
	html += '<p> Your current assests!';
	html += '<ul class="list-group">';
	$
			.each(
					data,
					function(k, v) {
						html += '<li class = "list-group-item list-group-item-success">';
						html += '<ul class="list-group">' + v.name;
						html += '<li class = "list-group-item list-group-item-light">Qty owned: '
								+ v.qty + '</li>';
						html += '<li class = "list-group-item list-group-item-light">Worth    : '
								+ v.value + '</li>';
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
			value : j,
			text : value.name
		}));
	});
}

// //////////// transaction related
// get all the transactions from the backend
function getAllTransactions() {
	var player = clientTurnJSON.player;
	var url = serviceUrl + 'rest/broker/transactions/' + player + '/all';
	$.ajax(url, {
		type : 'get',
		success : function(data) {
			updateTransactionsInUI(data);
		},
		error : function() {
			console.log('error when calling transactions');
		}
	});
}

function updateTransactionsInUI(data) {
	var html = "<h4>Transactions History</h4>";
	html += '<p>Your transactions so far'
	html += '<ul class="list-group">';
	$.each(data, function(k, v) {
		// html += '<li class = "list-group-item">';
		if (v.type == 'BUY') {
			html += '<li class = "list-group-item list-group-item-primary">';
			html += 'Bought ';
		} else {
			html += '<li class = "list-group-item list-group-item-danger">';
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