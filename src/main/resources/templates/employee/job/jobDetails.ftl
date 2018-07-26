<#import "../../macros/page.ftl" as p>
<#include "../../macros/security.ftl">

<@p.page>

<nav class="navbar navbar-expand-lg navbar-light bg-light mt-2">
    <a class="navbar-brand" href="/">Home</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <#if isDeveloper>
                  <li class="nav-item">
                      <a class="nav-link" href="/developer/resume">Resume</a>
                  </li>
            <#else>
                <li class="nav-item">
                    <a class="nav-link" href="/employee/jobs">Jobs</a>
                </li>
            </#if>
            <#if isDeveloper>
            <li class="nav-item">
                <a class="nav-link" href="/job/search">Find jobs</a>
            </li>
            <#else>
            <li class="nav-item">
                <a class="nav-link" href="/resume/search">Find Resumes</a>
            </li>
            </#if>
             <#if isDeveloper>
            <li class="nav-item">
                <a class="nav-link" href="/developer/resume/preview">Preview resume</a>
            </li>
             <#else>
             </#if>
               <#if isDeveloper>
               <#else>
             <li class="nav-item">
                 <a class="nav-link" href="/job/wizard/job-description/basic">Post job</a>
             </li>
               </#if>
        </ul>
        <div class="navbar-text mr-3">${email}</div>
        <form method="post" action="/logout">
            <input style="border-radius: 9px" class="btn btn-primary" type="submit" value="Sign out">
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
        </form>
    </div>
</nav>
     <div class="container h-100 " style=" font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;">
         <div style="width: 112%" class="row h-100 justify-content-center align-items-center">
             <form style="margin-top: 6%;border-radius:25px;background-color: white; padding: 30px;" class="col-5" action="/job/wizard/job-description/details"  method="post" >
                 <div style="color: #000;font-weight: normal; font-size: 23px;line-height: 28px; margin: 10px auto 13%;">Job Details
                     <span style="font-size: 14px; color:#666; margin: 29%">Step 2 of 3</span>
                 </div>
                 <div class="form-group">
                     <div class="form-group">
                         <label for="inputJobType" style="padding-top: 7%;color: #000;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 700;display: block;">
                             What type of job is it?
                         </label>
                         <select   style="color: #4b4b4b; border-radius: 8px" class="custom-select" id="inputJobType" name="jobType">
                             <option>Full-time</option>
                             <option>Part-time</option>
                             <option>Temporary</option>
                             <option>Contract</option>
                             <option>Internship</option>
                             <option>Commision</option>
                         </select>
                     </div>
                 </div>
                 <div class="form-row">
                     <span style="padding: 2% 0 0;">$</span>
                     <div class="form-group col-md-4">
                         <input pattern="^[ 0-9]+$" placeholder="Example: 50,000" style="border-radius: 8px" type="text" class="form-control" name="fromSalary" id="fromSalary">
                     </div>
                     <span style="padding: 2% 0 0;">To</span>
                     <div class="form-group col-md-4">
                         <input pattern="^[ 0-9]+$" placeholder="Example: 60,000" style="border-radius: 8px" type="text" class="form-control" name="toSalary" id="toSalary">
                     </div>
                     <div class="form-group col-md-2">
                         <select name="salaryPeriod" style="width: 175%; border-radius: 8px" id="inputState" class="custom-select">
                             <option selected value="1">per year</option>
                             <option value="12">per month</option>
                             <option value="52">per week</option>
                             <option value="365">per day</option>
                             <option value="8760">per hour</option>
                         </select>
                     </div>
                 </div>
                 <div class="form-group">
                     <div class="form-group">
                         <label for="textArea" style="padding-top: 7%;color: #000;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 700;display: block;">
                             Qualifications (optional)
                         </label>
                         <textarea  name="qualifications" style="border-radius: 8px;font-size: 14px;height: 110px;" class="form-control" id="textArea" rows="3"></textarea>
                     </div>
                 </div>
                 <div class="form-group">
                     <input type="hidden" name="_csrf" value="${_csrf.token}" />
                     <button type="submit" style="border-radius: 18px;width: auto"  class="btn btn-primary">Continue</button>
                 </div>
             </form>
         </div>
     </div>
</@p.page>