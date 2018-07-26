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
    ${success!}

<div class="container h-100 " style="padding-top: 1%; font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;">
    <div class="row h-100 justify-content-center align-items-center">
        <form method="post" action="/resume/search">
            <div class="form-row">
                <div class="col">
                    <label  for="inputWhat" style="color: #000;font-size: 1.4375rem;;letter-spacing: 0;line-height: 1.5rem;font-weight: 700;display: block;">What
                        <div>
                            <span style="color: #767676;font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;font-size: .75rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 400;display: inline-block">Job title, skills, or company</span>
                        </div>
                    </label>
                    <input name="whatDescription" id="inputWhat" style="background: url('https://image.flaticon.com/icons/svg/149/149401.svg') no-repeat scroll 5px 11px;padding-left: 40px;background-size: 19px;border-radius: 8px;font-size: .875rem;width: 25rem; height: 44px" type="text" class="form-control" placeholder="What">
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
                    <input type="submit" style="border-radius: 6.25rem; width: 121px; height: 44px"  class="btn btn-primary" value="find job">
                    <label  for="inputWhere" style="color: #000;font-size: 1.4375rem;;letter-spacing: 0;line-height: 1.5rem;font-weight: 700;display: block;">
                        <div style="padding: 12px">
                            <span style="color: #767676;font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;font-size: .75rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 400;display: inline-block"><a href="/resume/search/advanced">Advanced search</a></span>
                        </div>
                    </label>
                    <input type="hidden" name="_csrf" value="${_csrf.token}" />
                </div>
        </form>
        <!--MDB Cards-->
    </div>
    <div class="container mt-4">


        <div class="row" style="padding-left: 9%">
            <#list developers as developer>
                <div style="width: 30%;background-color: white;padding-top: 1%;padding-bottom: 1%;margin: 10% 1% 0;" class="jumbotron">
                    <h1 style="font-size: 15px"><#if (developer.desiredJob)??>${developer.desiredJob.desiredJobTitle!}<#else>${developer.user.email!}</#if> - ${developer.country!}, ${developer.city!}</h1>
                    <#list developer.workExperiences as work>
                        <p style="font-size: 12px" class="lead">${work.jobTitle!} - ${work.city!}</p>
                    </#list>
                    <hr class="my-4">
                    <#list developer.education as education>
                        <p style="font-size:12px" class="lead">${education.place!}, ${education.degree!} in ${education.fieldOfStudy!}</p>
                    </#list>
                    <div>
                        <a style="font-size: 14px" class="btn btn-primary" href="/resume/preview/${developer.id}" >Learn more</a>
                    </div>
                </div>
            </#list>
        </div>



    </div>
</div>






</@p.page>




