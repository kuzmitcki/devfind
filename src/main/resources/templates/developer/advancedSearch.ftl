<#import "../macros/page.ftl" as p>
<@p.page>
ADVANCED SEARCH
    <form id="advancedSearch" action="/job-search/advanced" method="post">
        <div>
            <input type="text" name="title" placeholder="title">
        </div>
        <div>
            <input type="text" name="fromSalary" pattern="^[ 0-9]+$" placeholder="from">
            <input type="text" name="toSalary" pattern="^[ 0-9]+$" placeholder="to">
            <select type="text" name="salaryPeriod">
                <option>per year</option>
                <option>per month</option>
                <option>per week</option>
                <option>per day</option>
                <option>per hour</option>
            </select>
        </div>
        <div>
            <input type="text" name="company" placeholder="company">
        </div>
        <div>
            <input id="keywords" type="text" name="keywords" placeholder="keywords" >
        </div>
        <div>
            <input type="text" name="fullDescription" placeholder="full">
        </div>
        <div>
            <input type="text" name="location" placeholder="location">
        </div>
        <input type="submit" value="find job">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
    </form>
    <script>
        $(function () {
            $('#advancedSearch').validate({
                rules: {
                    keywords: {
                        required: true
                    },
                    title: {
                        required: true
                    }
                },
                messages:{
                    keywords:{
                        required: "Please fill this something"
                    },
                    title: {
                        required: "Please enter the title"
                    }
                }
            });
        });
    </script>
</@p.page>