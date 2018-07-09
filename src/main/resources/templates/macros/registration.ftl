<#macro registration path>
<form id="registrationForm" action="${path}" method="post">
    <div><label> User Name : <input type="text" name="username" id="username"/> </label></div>
    <div><label> Email: <input type="text" name="email" id="email"></label></div>
    <div><label> Password: <input type="password" name="password" id="password"/> </label></div>
    Repeat password : <input type="password" id="password_repeat">
    <input type="submit" value="Registration">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />

    <script>
        $(function () {
            $('#registrationForm').validate({
                rules: {
                    username: "required",
                    email: {
                        required: true,
                        email: true
                    },
                    password: "required"
                },
                messages: {
                    email:{
                        required: "Fill this field",
                        email: "Please enter <em>valid</em> email"
                    }
                }
            });
        });
    </script>
</form>
</#macro>