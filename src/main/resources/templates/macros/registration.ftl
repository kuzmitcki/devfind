<#macro registration path>
<form action="${path}" method="post">
    <div><label> User Name : <input type="text" name="username"/> </label></div>
    <div><label> Password: <input type="password" name="password"/> </label></div>
    <div><label> Email: <input type="email" name="email"></label></div>
    <input type="submit" value="Registration">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
</form>
</#macro>