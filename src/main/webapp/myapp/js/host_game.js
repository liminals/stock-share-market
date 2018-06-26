var checkPlayersTimer = setInterval(checkForPlayers, 1000 * 3);

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