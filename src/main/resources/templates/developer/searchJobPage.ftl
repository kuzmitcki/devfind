<#import "../macros/page.ftl" as p>
<@p.page>
    ${message!}
    <form method="post" action="/job-search">
        <input type="text" name="whatDescription" placeholder="what">
        <input type="text" name="whereDescription" placeholder="where">
        <input type="submit" placeholder="Search">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
    </form>
    <a href="/job-search/advanced">Advanced Search</a>

    <#list jobs! as job>
        <span>${job.title}</span>
    </#list>
</@p.page>