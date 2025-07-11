<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head><title>Счет матча</title></head>
<body>
<h2>Матч между ${player1} и ${player2}</h2>
<p>Текущий счет: ${score}</p>

<form method="post" action="${pageContext.request.contextPath}/match-score?uuid=${param.uuid}">
    <button name="pointWinner" value="player1" type="submit">Игрок 1 выиграл очко</button>
    <button name="pointWinner" value="player2" type="submit">Игрок 2 выиграл очко</button>
</form>

</body>
</html>