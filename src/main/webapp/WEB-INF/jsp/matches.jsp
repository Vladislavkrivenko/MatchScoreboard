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

<table border="1" cell padding="5" cells pacing="0">
    <thead>
        <tr>
            <th>Матч ID</th>
            <th>Игрок 1</th>
            <th>Игрок 2</th>
            <th>Счёт</th>
        </tr>
    </thead>
    <tbody>
        <c:choose>
            <c:when test="${empty matches}">
                <tr><td cols pan="4">Матчи не найдены</td></tr>
            </c:when>
            <c:otherwise>
                <c:forEach var="match" items="${matches}">
                    <tr>
                        <td>${match.id}</td>
                        <td>${match.player1}</td>
                        <td>${match.player2}</td>
                        <td>${match.score}</td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </tbody>
</table>

</body>
</html>