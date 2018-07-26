<#macro search path isResumeSearch>
     <div class="container h-100 " style="padding-top: 18%; font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;">
         <div class="row h-100 justify-content-center align-items-center">
             <form  method="post" action="${path}">
                 <div class="form-row">
                     <div class="col">
                         <label  for="inputWhat" style="color: #000;font-size: 1.4375rem;;letter-spacing: 0;line-height: 1.5rem;font-weight: 700;display: block;">What
                             <div>
                                 <span style="color: #767676;font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;font-size: .75rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 400;display: inline-block">Job title, skills, or company</span>
                             </div>
                         </label>
                         <input required name="whatDescription" id="inputWhat" style="background: url('https://image.flaticon.com/icons/svg/149/149401.svg') no-repeat scroll 5px 11px;padding-left: 40px;background-size: 19px;border-radius: 8px;font-size: .875rem;width: 25rem; height: 44px" type="text" class="form-control" placeholder="What">
                     </div>
                     <div class="col">
                         <label  for="inputWhere" style="color: #000;font-size: 1.4375rem;;letter-spacing: 0;line-height: 1.5rem;font-weight: 700;display: block;">Where
                             <div>
                                 <span style="color: #767676;font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;font-size: .75rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 400;display: inline-block">City, state, or zip</span>
                             </div>
                         </label>
                         <input name="whereDescription" id="inputWhere" style="border-radius: 8px;background: url('https://image.flaticon.com/icons/svg/684/684809.svg') no-repeat scroll 5px 11px;background-size: 19px;padding-left: 40px;font-size: .875rem;width: 25rem; height: 44px" type="text" class="form-control" placeholder="Where">
                     </div>
                     <div class="col" style="padding-top: 6%;">
                         <input type="submit" style="border-radius: 6.25rem; width: 121px; height: 44px"  class="btn btn-primary" value="<#if isResumeSearch>find resume<#else>find job</#if>">
                         <label  for="inputWhere" style="color: #000;font-size: 1.4375rem;;letter-spacing: 0;line-height: 1.5rem;font-weight: 700;display: block;">
                             <div style="padding: 12px">
                                 <span style="color: #767676;font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;font-size: .75rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 400;display: inline-block"><a href="<#if isResumeSearch>/resume/search/advanced<#else>/job/search/advanced</#if>">Advanced search</a></span>
                             </div>
                         </label>
                         <input type="hidden" name="_csrf" value="${_csrf.token}" />
                     </div>
             </form>
         </div>
     </div>
</#macro>