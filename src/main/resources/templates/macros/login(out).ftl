<#macro login path>


    <div class="container h-100 " style="padding-top: 5%; font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;">
        <div class="row h-100 justify-content-center align-items-center">
            <img style="width: 39.48%;  padding-top: 150px;" src="http://images.clipartpanda.com/computer-monitor-png-Modern_Desktop.png">
            <form  action="${path}" style="margin-left: 20%;border-radius:25px;margin-top:10%;background-color: white; padding: 30px;"   method="post">
                <div style="text-align:center;color: #000;font-weight: 700;" class="form-group">
                    Sign In
                </div>
                <div style="text-align: center">
                    <span style="font-size: 14px;color: red">${message!}${activate!}</span>
                </div>
                <div class="form-group">
                    <div class="form-group">
                        <label for="signinId" style="color: #4b4b4b;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 700;display: block;">Username</label>
                        <input style=" border-radius: 8px;" type="text" class="form-control" name="username" id="signinId" >
                    </div>
                </div>
                <div class="form-group">
                    <label  for="signinPwd" style="color: #4b4b4b;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 700;display: block;">Password</label>
                    <input style="border-radius: 8px;" type="password" class="form-control" name="password" id="signinPwd">
                </div>
                <div class="col-auto my-1">
                    <div class="custom-control custom-checkbox mr-sm-2">
                        <input checked type="checkbox"  name="check"  class="custom-control-input" id="customControlAutosizing">
                        <label style="margin-bottom: 10px;padding-top: 2px;font-size: 14px" class="custom-control-label" for="customControlAutosizing">Remember me?</label>
                    </div>
                </div>
                <div style="font-size: 0.85rem" class="form-group">
                    <input type="hidden" name="_csrf" value="${_csrf.token}" />
                    <button type="submit" style="border-radius: 18px;" class="btn btn-primary">Sign in</button>
                    <a style="margin: 12px;font-size: 15px;color:cornflowerblue;" href="/registration">Have no account? Sign up</a>
                </div>
            </form>
        </div>
    </div>
<script>
    $(function () {

        if (localStorage.chkbox && localStorage.chkbox != '') {
            $('#customControlAutosizing').attr('checked', 'checked');
            $('#signinId').val(localStorage.username);
            $('#signinPwd').val(localStorage.pass);
        } else {
            $('#customControlAutosizing').removeAttr('checked');
            $('#signinId').val('');
            $('#signinPwd').val('');
        }
        $('#customControlAutosizing').click(function () {
            if ($('#customControlAutosizing').is(':checked')) {
                // save username and password
                localStorage.username = $('#signinId').val();
                localStorage.pass = $('#signinPwd').val();
                localStorage.chkbox = $('#customControlAutosizing').val();
            } else {
                localStorage.username = '';
                localStorage.pass = '';
                localStorage.chkbox = '';
            }
        });
    });
</script>
</#macro>

<#macro logout>
    <form method="post" action="/logout">
        <input type="submit" value="Sign out">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
    </form>
</#macro>

