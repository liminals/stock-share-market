var timer = setInterval(checkGames, 1000 * 3);
var checkPlayersTimer = setInterval(checkForPlayers, 1000 * 3);

var selectedGame;
var hostedGames = [];
var joinedPlayers = [];

$('#joinGame').prop('disabled', 'true');

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
	var html = '<div id=' + "gamesHosted" + '>';
	html += '<ul>';
	$.each(gamesData, function(key, value) {
		var json = JSON.stringify(value);
		html += '<li>Game : ' + value.id; + '<li>';
		html += '<ul><li>Turns : ' + value.turns; + '</li>';
		html += '<li><ul>Players';
		
		var players = JSON.parse(value.playersJSON);
		$.each(players, function(k, v) {
			html += '<li>' + v + '</li>';
		});
		html += '</ul></li>';
		html += '</ul>';
	})
	html += '</div>';
	$("#gamesInfoArea").html(html);
	$('#gamesHosted').on('change', function() {
		var values = JSON.parse(this.value);
		selectedGame = this.value;
		loadGamesInfo(values);
		// $('#joinGame').prop('disabled', false);
	});
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

function loadHostedGameInfo(data) {
	var html = '<ul>Game ' + data.id;
	html += '<li>Turns: ' + data.turns + '</li>';
	html += '</ul>'
	$("#gameInfo").html(html);
	loadJoinedPlayers(data.players);
}

function loadJoinedPlayers(data) {
	var html2 = '<ul>Players';
	var players = JSON.parse(data);
	$.each(players, function(k, v) {
		html2 += '<li>' + v + '</li>';
	});
	html2 += '</ul>';
	$("#joinedPlayers").html(html2);
}

function checkForPlayers() {
	if (typeof hostedGameInfo !== 'undefined'){
	var url = serviceUrl + 'game/' + hostedGameInfo.id + '/checkForPlayers';
	$.ajax(url, {
		type: 'get',
		success: function(data) {
			loadJoinedPlayers(data);
		},
		error: function() {
			console.log('error in loading');
		}
	});
	}
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
		//loadJoinedPlayers(joinedPlayers);
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