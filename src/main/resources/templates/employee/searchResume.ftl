<#import "../macros/page.ftl" as p>
<@p.page>
    <form method="post" action="/resume">
        <input type="text" name="whatDescription">
        <input type="text" name="whereDescription">
        <input type="submit" value="Find developers">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </form>
    <a href="/resume/advanced">Advanced search</a>
</@p.page>