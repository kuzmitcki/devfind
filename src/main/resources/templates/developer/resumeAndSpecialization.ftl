<#import "../macros/page.ftl" as p>
<@p.page>
    <#list specializations as specialization>
        ${specialization.skill}
        <a href="/resume/edit/${specialization.id}">Edit</a>
    </#list>
    <a href="/edit-developer/add">Add new resume</a>
</@p.page>