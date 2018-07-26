<#assign
known = Session.SPRING_SECURITY_CONTEXT??
>

<#if known>
    <#assign
    userSec = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    email = userSec.getEmail()
    isDeveloper = userSec.isDeveloperRole()
    >
<#else>
    <#assign
    name = "unknown"
    isDeveloper = false
    >
</#if>