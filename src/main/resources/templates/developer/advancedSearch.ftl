<#import "../macros/page.ftl" as p>
<@p.page>
ADVANCED SEARCH
    <form action="/job-search/advanced" method="post">
        <div>
            <input type="text" name="title">
        </div>
        <div>
            <select type="text" name="salaryPeriod">
                <option>per year</option>
                <option>per month</option>
                <option>per week</option>
                <option>per day</option>
                <option>per hour</option>
            </select>
        </div>
        <div>
            <input type="text" name="company">
        </div>
        <div>
            <input type="text" name="keywords">
        </div>
        <div>
            <input type="text" name="fullDescription">
        </div>
        <div>
            <input type="text" name="location">
        </div>
        <input type="submit" value="find job">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
    </form>
</@p.page>