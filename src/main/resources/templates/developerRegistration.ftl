<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<form action="/developer" method="post">
    <div><label> User Name : <input type="text" name="username"/> </label></div>
    <div><label> Password: <input type="password" name="password"/> </label></div>
    <div><label>Email: <input type="email" name="email"></label></div>
    <input type="submit" value="Registration">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
</form>
</body>
</html>