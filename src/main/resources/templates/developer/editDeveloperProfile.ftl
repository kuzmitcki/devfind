<#import "../macros/page.ftl" as p>
<#import "../macros/login(out).ftl" as l>
<@p.page>
    ${message!}
    <form method="post" action="/edit-developer">
        <input type="text"  name="skill">
        <input type="text"  value="${user.developer.firstName!}" name="firstName">
        <input type="text"  value="${user.developer.lastName!}" name="lastName">
        <input type="text"  value="${user.developer.company!}" name="company">
        <input type="email" value="${user.developer.email!}" name="email" >
        <select name="gender">
            <option>Male</option>
            <option>Female</option>
        </select>
        <input type="text" value="${user.developer.birthday!}" name="birthday" pattern="[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])" placeholder="Format: YYYY-MM-DD">
        <input type="submit" value="submit">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
    </form>
    <@l.logout></@l.logout>
</@p.page>