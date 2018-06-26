var timer = setInterval(checkGames, 1000 * 3);

var selectedGame;
var hostedGames = [];
var joinedPlayers = [];

$('#joinGame').prop('disabled', true);
$('#hostGame').prop('disabled', true);

// this will check for games being hosted real time
function checkGames() {
	var url = serviceUrl + 'game/getGames';
	$.ajax(url, {
		type: 'post',
		success: function(gamesData) {
			if (gamesData.length > 0) {
				if (hostedGames.length > 0) {
					$.each(gamesData, function(k, v){
						checkIfGameAlreadyHosted(v, hostedGames);
					});
				} else {
					hostedGames = gamesData;
					loadGamesUI(hostedGames);
				}
			}
		},
		error: function() {
			console.log('an error occured!, when checking for games');
		}
	})
}

// this will push new games into the hosted games array
function checkIfGameAlreadyHosted(recievedGame, hostedGames) {
	var found = 0;
	$.each(hostedGames, function(k, value) {
		if (recievedGame.id == value.id) {
			found = 1;
		}
	});
	if (found == 0) {
		console.log('pushing ' + recievedGame);
		hostedGames.push(recievedGame);
		loadGamesUI(hostedGames);
	}
}

function loadGamesUI(gamesData) {
	var html = '<div class="row">';
	$.each(gamesData, function(k, value){
		var json = JSON.stringify(value);
		html += '<div class="col-md-3">';
		html += 	'<div class="card" style="width: 15rem;">';
		html += 			'<div class="card-body">';
		html += 			'<h5 class="card-title">' + 'Game ' + value.id + '</h5>';
		html += 			'<p class="card-text"> Game hosted by ' + value.createdBy;
		html += 			'<p class="card-text">Current Players';
		html += 			'<ul class="list-group">';
		var players = JSON.parse(value.playersJSON);
		$.each(players, function(k, v) {
			html += 			'<li class="list-group-item">' + v + '</li>';
		});
		html += 			'</ul>';
		html += 		'</div>';
		html += 	'</div>';
		html += '</div>';
	});
	html += '</div>';
	$("#gamesInfoArea").html(html);
}

function loadGamesInfo(data) {
	var html = '<ul>Game ' + data.id;
	html += '<li>Turns: ' + data.turns + '</li>';
	html += '<ul>Players: ';
	console.log(data);
	var players = JSON.parse(data.playersJSON);
	$.each(players, function(k, v){
		html += '<li>' + v + '</li>';
	});
	html += '</ul>';
	html += '</ul>';
	$("#selectedGame").html(html);
}

function checkIfPlayerJoined(player, playersJson) {
	var found = 0;
	$.each(playersJson, function(k, value) {
		if (player == value) {
			found = 1;
			console.log('found');
		}
	});
	if (found == 0) {
		console.log('pushing ' + player);
		joinedPlayers.push(player);
	}
}

$('#joinGameId').on('input propertychange paste', function() {
	var typedValue = $.trim($(this).val());
	var reg = new RegExp('^[0-9]+$');
	if(typedValue != '') {
		if (reg.test(typedValue)) {
			console.log('matches');
			$('#joinGame').prop('disabled', false);
		} else {
			console.log('not matches');
			$('#joinGame').prop('disabled', true);
		}
	} else {
		$('#joinGame').prop('disabled', true);
	}
});

$('#turnsId').on('input propertychange paste', function() {
	var typedValue = $.trim($(this).val());
	var reg = new RegExp('^[0-9]+$');
	if(typedValue != '') {
		if (reg.test(typedValue)) {
			console.log('matches');
			$('#hostGame').prop('disabled', false);
		} else {
			console.log('not matches');
			$('#hostGame').prop('disabled', true);
		}
	} else {
		$('#hostGame').prop('disabled', true);
	}
});