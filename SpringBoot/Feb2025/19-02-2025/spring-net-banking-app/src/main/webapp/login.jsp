<html>
<head>
    <title>Login</title>
</head>
<body>
    <h1>Login</h1>

    <form action="/submit_login" method="GET">
        Enter Customer ID: 
        <input type="text" name="cid" required>
        <br><br>

        Enter Password: 
        <input type="password" name="pwd" required>
        <br><br>

        <input type="submit" value="Login">
    </form>

    <h3 style="color: red;"><%= request.getAttribute("loginError") != null ? request.getAttribute("loginError") : "" %></h3>

    <h4>New User? <a href="/signup">Signup here</a></h4>
</body>
</html>
