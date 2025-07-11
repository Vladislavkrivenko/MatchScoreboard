<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
<title>New Match</title>
</head>
<body>
 <form action="${pageContext.request.contextPath}/new-match" method="post">
   <label for="firstId">Имя первого игрока:
     <input type="text" name="nameFirstPlayer" id="firstId" required>
    </label><br>

   <label for="secondId">Имя второго игрока:
    <input type="text" name="nameSecondPlayer" id="secondId" required>
   </label><br>

   <button type="submit">Начать новий матч</button>
 </form>
</body>
</html>
