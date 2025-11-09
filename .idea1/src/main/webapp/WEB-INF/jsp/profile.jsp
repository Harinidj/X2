<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>Profile</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="includes/header.jsp" %>
<div style="padding:30px;display:flex;justify-content:center;">
  <div style="width:700px;">
    <div class="profile-card">
      <h2>@${user.username}</h2>
      <p>Email: ${user.email}</p>
      <p>Joined: ${user.createdAt}</p>
    </div>

    <div style="margin-top:20px;">
      <h3>Your posts</h3>
      <c:forEach var="p" items="${posts}">
        <div class="post-card">
          <div class="meta"><span class="time">${p.timestamp}</span></div>
          <div>${p.content}</div>
        </div>
      </c:forEach>
    </div>
  </div>
</div>
</body>
<%@ include file="includes/footer.jsp" %>
</html>
