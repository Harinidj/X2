<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><meta charset="utf-8"><title>Login</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="includes/header.jsp" %>
<div class="auth-card">
  <h2>Login</h2>
  <form action="${pageContext.request.contextPath}/doLogin" method="post">
    <input name="username" placeholder="username" required/>
    <input type="password" name="password" placeholder="password" required/>
    <button type="submit">Login</button>
  </form>
  <c:if test="${not empty error}"><div class="error">${error}</div></c:if>
  <p style="margin-top:10px">Don't have an account? <a href="${pageContext.request.contextPath}/register">Register</a></p>
</div>
</body>
<%@ include file="includes/footer.jsp" %>
</html>
