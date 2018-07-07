<#import "../macros/page.ftl" as p>
<@p.page>
    <#list jobs! as job>
       <div>
           ${job.title!}
       </div>
    </#list>
</@p.page>