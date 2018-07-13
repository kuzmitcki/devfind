<#import "../macros/page.ftl" as p>
<#import "../macros/address.ftl" as a>
<@p.page>
    ${message!}
    <@a.address "/edit-employee"></@a.address>
</@p.page>