<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<form method="post" action="/information">
    <input type="text" name="skill">
    <input type="text" name="start">
    <input type="text" name="end">
    <input type="number" name="schedule">
    <input type="submit" value="Add information">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
</form>

</body>
</html>