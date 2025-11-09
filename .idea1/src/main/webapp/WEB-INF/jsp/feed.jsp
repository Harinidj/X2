<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>X² | Feed</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #e6ecf0;
            margin: 0;
            padding: 0;
        }
        .navbar {
            background-color: #1DA1F2;
            color: white;
            padding: 15px 25px;
            font-size: 22px;
            display: flex;
            justify-content: space-between;
        }
        .feed-container {
            width: 600px;
            margin: 40px auto;
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
            padding: 20px;
        }
        .post-box textarea {
            width: 100%;
            border: none;
            outline: none;
            resize: none;
            padding: 12px;
            font-size: 16px;
            border-bottom: 1px solid #ccc;
        }
        .post-box button {
            background-color: #1DA1F2;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 25px;
            margin-top: 10px;
            float: right;
            cursor: pointer;
        }
        .post {
            border-bottom: 1px solid #ddd;
            padding: 15px 0;
        }
        .username {
            color: #1DA1F2;
            font-weight: bold;
        }
        .timestamp {
            color: #999;
            font-size: 13px;
        }
        .logout-btn {
            color: white;
            text-decoration: none;
            background: rgba(255,255,255,0.2);
            padding: 8px 15px;
            border-radius: 20px;
            font-size: 14px;
            border: none;
            cursor: pointer;
        }
    </style>
</head>
<body>

<div class="navbar">
    <div>X<sup>2</sup></div>
    <form action="/logout" method="post">
        <button class="logout-btn">Logout</button>
    </form>
</div>

<div class="feed-container">
    <h2>Welcome, ${username} 👋</h2>

    <form action="/addPost" method="post" class="post-box">
        <textarea name="content" rows="3" placeholder="What's on your mind?" required></textarea>
        <button type="submit">Post</button>
    </form>

    <hr/>

    <c:forEach var="post" items="${posts}">
        <div class="post">
            <span class="username">@${post.username}</span>
            <p>${post.content}</p>
            <span class="timestamp">${post.timestamp}</span>
        </div>
    </c:forEach>
</div>

</body>
</html>
