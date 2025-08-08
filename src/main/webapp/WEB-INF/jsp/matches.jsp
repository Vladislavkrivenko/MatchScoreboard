<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Список матчей</title>
</head>
<body>
<h1>Список завершенных матчей</h1>

<form method="get" action="${pageContext.request.contextPath}/matches">
    <label for="filter">Фильтр по имени игрока:</label>
    <input type="text" id="filter" name="filter_by_player_name" value="${param.filter_by_player_name}">
    <button type="submit">Искать</button>
</form>

<table border="1" cellpadding="5" cellspacing="0">
    <thead>
    <tr>
        <th>Матч ID</th>
        <th>Игрок 1</th>
        <th>Игрок 2</th>
        <th>Победитель</th>
    </tr>
    </thead>
    <tbody>
    <c:choose>
        <c:when test="${empty matchesDto.matches}">
            <tr>
                <td colspan="4">Матчи не найдены</td>
            </tr>
        </c:when>
        <c:otherwise>
            <c:forEach var="match" items="${matchesDto.matches}">
                <tr>
                    <td>${match.matchId}</td>
                    <td>${match.player1Name}</td>
                    <td>${match.player2Name}</td>
                    <td>${match.winnerName}</td>
                </tr>
            </c:forEach>
        </c:otherwise>
    </c:choose>
    </tbody>
</table>

</body>
</html>