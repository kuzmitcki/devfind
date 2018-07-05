<#import "../macros/page.ftl" as p>
<@p.page>
    <form method="post" action="/employee-job-main">
        <input type="text" name="title"   placeholder="Brief the specific title">
        <input type="text" name="company" placeholder="Name of company for this job positing">
        <input type="text" name="desiredExperience" placeholder="">
        <input type="text" name="shortDescription" placeholder="short description for this job positing">
        <input type="text" name="fromSalary" placeholder="Example: 50000 $" pattern="^[ 0-9]+$">
        <input type="text" name="toSalary" placeholder="Example: 100000 $" pattern="^[ 0-9]+$">
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
</@p.page>