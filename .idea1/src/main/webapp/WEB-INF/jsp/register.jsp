<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><meta charset="utf-8"><title>Register</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="includes/header.jsp" %>
<div class="auth-card">
  <h2>Register</h2>
  <form action="${pageContext.request.contextPath}/doRegister" method="post">
    <input name="username" placeholder="username" required/>
    <input name="email" type="email" placeholder="email" required/>
    <input type="password" name="password" placeholder="password" required/>
    <button type="submit">Register</button>
  </form>
  <c:if test="${not empty error}"><div class="error">${error}</div></c:if>
  <c:if test="${not empty msg}"><div class="info">${msg}</div></c:if>
</div>
</body>
<%@ include file="includes/footer.jsp" %>
</html>
