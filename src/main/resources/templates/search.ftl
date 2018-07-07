<#import "macros/page.ftl" as p>
<#--<#import "macros/login(out).ftl" as l>-->
<@p.page>

    <#list developers as developer>
        <span>${developer.username}</span>
        <span><#list developer.specializations as spec>
            <span>${spec}<#sep>, </span>
        </#list>
        </span>
    </#list>

</@p.page>