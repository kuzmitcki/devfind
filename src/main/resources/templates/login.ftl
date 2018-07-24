<#import "macros/page.ftl" as p>
<#import "macros/login(out).ftl" as l>
<@p.page>
    ${message!}
    <@l.login "/login"/>
</@p.page>
