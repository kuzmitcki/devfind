<#import "macros/page.ftl" as p>
<@p.page>
  <div class="container h-100 " style="padding-top: 5%; font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;">

      <div class="row h-100 justify-content-center align-items-center">
          <form id="registrationForm" style="border-radius:25px;margin-top:10%;background-color: white; padding: 30px;" class="col-4"  method="post">
              <div style="text-align:center;color: #000;font-weight: 700;" class="form-group">
                  Registration
              </div>
              <div style="text-align: center">
                  <span style="font-size: 14px;color: red">${message!}</span>
              </div>
              <div class="form-group">
                  <div class="form-group">
                      <label for="inputTitle" style="color: #4b4b4b;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 700;display: block;">Username</label>
                      <input style=" border-radius: 8px;" type="text" class="form-control" name="username" id="inputTitle" >
                  </div>
              </div>
              <div class="form-group">
                  <label  for="city" style="color: #4b4b4b;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 700;display: block;">
                      Email
                  </label>
                  <input required placeholder="some@some.com"  style="color: #4b4b4b; border-radius: 8px" class="form-control" id="city" type="email" name="email">
              </div>
              <div class="form-group">
                  <label  for="password" style="color: #4b4b4b;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 700;display: block;">Password</label>
                  <input minlength="5" required style="border-radius: 8px;" type="password" class="form-control" name="password" id="password">
              </div>
              <div class="form-group">
                  <label  for="confirm_password" style="color: #4b4b4b;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 700;display: block;">Repeat password</label>
                  <input required style="border-radius: 8px;" type="password" class="form-control" name="repeat" id="confirm_password">
              </div>
              <div class="form-group row">
                  <div class="col-sm-8">
                      <fieldset id="role">
                          <label>
                              Developer
                              <input checked name="role" type="radio" value="1">
                          </label>
                          <label style="margin-left: 4%">Employee
                              <input name="role" type="radio" value="2">
                          </label>
                      </fieldset>
                  </div>
              </div>

              <div style="font-size: 0.85rem" class="form-group">
                  <input type="hidden" name="_csrf" value="${_csrf.token}" />
                  <button type="submit" style="border-radius: 18px;  margin-top: 10px;" class="btn btn-primary">Create account</button>
              </div>
          </form>
      </div>
  </div>

 <script type="text/javascript">
     var password = document.getElementById("password")
             , confirm_password = document.getElementById("confirm_password");

     function validatePassword(){
         if(password.value != confirm_password.value) {
             confirm_password.setCustomValidity("Passwords Don't Match");
         } else {
             confirm_password.setCustomValidity('');
         }
     }

     password.onchange = validatePassword;
     confirm_password.onkeyup = validatePassword;
 </script>

</@p.page>
