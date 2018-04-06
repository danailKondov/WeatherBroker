<%--
  Created by IntelliJ IDEA.
  User: DKondov
  Date: 05.04.2018
  Time: 16:27
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Enter city</title>
</head>
<body>
<h1>Enter city for weather info</h1>
<form action="${pageContext.servletContext.contextPath}/weather/cityname">
    <input type="text" name="cityName" placeholder="City name">
    <input type="submit" value="Submit">
</form>
</body>
</html>
