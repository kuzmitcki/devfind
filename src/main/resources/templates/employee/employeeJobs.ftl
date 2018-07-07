<#import "../macros/page.ftl" as p>
<@p.page>
<#list jobs as job>
   <div>
       <span>${job.title}</span>
       <input type="hidden" value="${job.id}">
       <a href="/employee-jobs/edit/${job.id}">Edit</a>
   </div>
</#list>

</@p.page>