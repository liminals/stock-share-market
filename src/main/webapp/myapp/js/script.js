var turn = 1;
var timer = setInterval(countTurns, 1000 * 4);

function countTurns() {
	if (turn == 5)
		clearInterval(timer);
	$("#currentTurn").text("Current Turn: " + turn);
	console.log("turn " + turn++);
}