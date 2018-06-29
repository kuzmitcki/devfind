<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<form method="post" action="/information">
    <label>
        <input type="text" name="skill">
    </label>
    <input type="text" name="start" placeholder="HH:MM:SS" pattern="^(?:(?:([01]?\d|2[0-3]):)?([0-5]?\d):)?([0-5]?\d)$" id="24h"/>
    <input type="text" name="end"   placeholder="HH:MM:SS" pattern="^(?:(?:([01]?\d|2[0-3]):)?([0-5]?\d):)?([0-5]?\d)$" id="24h">
    <input type="text" name="schedule" placeholder="Full time or part time">
    <input type="submit" value="Add information">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
</form>
</body>
</html>