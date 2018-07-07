<#macro editAdd path isAddForm>
     <form method="post" action="${path}">
         <input type="text" name="title" value="<#if isAddForm><#else>${job.title}</#if>" placeholder="Brief the specific title">
         <input type="text" name="company" value="<#if isAddForm><#else>${job.company!}</#if>" placeholder="Name of company for this job positing">
         <input type="text" name="desiredExperience" value="<#if isAddForm><#else>${job.desiredExperience!}</#if>" placeholder="">
         <input type="text" name="shortDescription" value="<#if isAddForm><#else>${job.shortDescription!}</#if>" placeholder="short description for this job positing">
         <input type="text" name="fromSalary" value="<#if isAddForm><#else>${job.fromSalary!}</#if>" placeholder="Example: 50000 $" pattern="^[ 0-9]+$">
         <input type="text" name="toSalary" value="<#if isAddForm><#else>${job.toSalary!}</#if>"  placeholder="Example: 100000 $" pattern="^[ 0-9]+$">
         <select name="salaryPeriod">
             <option>per year</option>
             <option>per month</option>
             <option>per week</option>
             <option>per day</option>
             <option>per hour</option>
         </select>
         <select name="jobType">
             <option>Full-time</option>
             <option>Part-time</option>
             <option>Temporary</option>
             <option>Contract</option>
             <option>Internship</option>
             <option>Commision</option>
         </select>
         <div>
             <textarea name="fullDescription">Some text...</textarea>
         </div>
         <input type="submit" value="submit">
         <input type="hidden" name="_csrf" value="${_csrf.token}" />
     </form>
</#macro>