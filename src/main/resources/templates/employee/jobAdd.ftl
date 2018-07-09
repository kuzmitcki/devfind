    <#import "../macros/page.ftl" as p>
    <#import "../macros/edit(add).ftl" as a>
    <@p.page>
    ${message!}
        <@a.editAdd "/employee-job-main" true />
    </@p.page>