<#import "macros/registration.ftl" as r>
<#import "macros/page.ftl" as p>
<@p.page>
    ${message!}
    <@r.registration "/employee"/>
</@p.page>