<#import "../macros/page.ftl" as p>
<@p.page>
    ${message!}


    <form method="post" action="/edit-developer/add">
        <input type="text" name="skills">
        <div>
            <label for="advanced">Advanced example:</label>
            <textarea id="advanced" name="advanced"
                      rows="3" cols="33" maxlength="10000"
                      wrap="hard">

            </textarea>
        </div>
        <input type="submit" value="add">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
    </form>
</@p.page>