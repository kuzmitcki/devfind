<#import "../macros/page.ftl" as p>
<@p.page>


<#list  developers as developer>
<div>
    ${developer.firstName}
    ${developer.lastName}
</div>
</#list>
</@p.page>