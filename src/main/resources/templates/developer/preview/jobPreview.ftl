<#import '../../macros/page.ftl' as p>
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
    <div class="container h-100 " style="padding-top: 5%; font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;">
        <div  class="row h-100 justify-content-center align-items-center">
            <div style="width: 90%; background-color: white">
                <div style="margin-left: 25px;margin-top: 20px;">
                    <div style="color: red; margin-bottom: 1%">
                        ${message!}
                    </div>
                    <div>
                        <strong>Company: </strong> ${employee.company!}
                    </div>
                    <div>
                        <strong>Job Title: </strong>${job.title!}
                    </div>
                    <div>
                        <strong>Location: </strong> ${job.country}, ${job.jobLocation}
                    </div>
                </div>
                <div style="margin-left: 25px;padding-top: 4%;">
                    <strong>Job Description:</strong>
                    <div style="padding-top: 1%;padding-right: 3%">
                        <pre style="font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 400;">${job.fullDescription!}</pre>
                    </div>
                </div>
                <div style="margin-left: 25px;padding-top: 4%">
                    <strong>Desired Experience:</strong>
                    <div style="padding-top: 1%; padding-right: 3%">
                        <pre style="font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 400;">${job.desiredExperience!}</pre>
                    </div>

                </div>
                <div style="margin-left: 25px;padding-top: 4%">
                    <strong>Qualifications:</strong>
                    <div style="padding-top: 1%;padding-right: 3%">
                        <pre style="font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 400;">${job.qualifications!}</pre>
                    </div>
                </div>
                <form method="post" action="/job/send-resume/${job.id}" style="margin-left: 25px; padding-top: 5%">
                    <div class="form-group">
                        <input type="hidden" name="_csrf" value="${_csrf.token}" />
                        <button type="submit" style="border-radius: 18px;width: auto"  class="btn btn-primary">Respond</button>
                    </div>
                </form>
            </div>
        </div>

</@p.page>