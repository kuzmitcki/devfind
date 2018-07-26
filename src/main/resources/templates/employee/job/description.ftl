<#import  "../../macros/page.ftl" as p>
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

    <div class="container h-100 " style="font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;">
        <div class="row h-100 justify-content-center align-items-center">
            <form style="margin-top: 6%;border-radius:25px;background-color: white; padding: 30px;" class="col-5" action="/job/wizard/description"  method="post" >
                <div class="form-group">
                    <div style="color: #000;font-weight: normal; font-size: 23px;line-height: 28px; margin: 10px auto 13%;">Job Description
                        <span style="font-size: 14px; color:#666; margin: 16%">Step 3 of 3</span>
                    </div>
                    <div>
                        ${job.title!} - <span style="color: #777">${job.country}, ${job.jobLocation!}</span>
                    </div>
                    <div class="form-group">
                        <label for="textArea" style="padding-top: 7%;color: #000;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 700;display: block;">
                            Desired experience
                            <div>
                                <span style="color: #767676;font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;font-size: .75rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 400;display: inline-block">Describe the desired experience and skills of your ideal developer.</span>
                            </div>
                        </label>
                        <textarea  name="desiredExperience" style="border-radius: 8px;font-size: 14px;height: 110px;" class="form-control" id="textArea" rows="3"></textarea>
                    </div>
                    <div class="form-group">
                        <label for="textArea" style="padding-top: 7%;color: #000;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 700;display: block;">
                            Full description  <img style="margin-left: 5px;width: 14px" src="https://cdn3.iconfinder.com/data/icons/web-interface-6/10/star-red-512.png">
                            <div>
                                <span style="color: #767676;font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;font-size: .75rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 400;display: inline-block">Describe the responsibilities of this job, required work experience, skills, or education.</span>
                            </div>
                        </label>
                        <textarea  name="fullDescription" style="border-radius: 8px;font-size: 14px;height: 280px;" class="form-control" id="textArea" rows="3"></textarea>
                    </div>
                </div>
                <div class="form-group">
                    <input type="hidden" name="_csrf" value="${_csrf.token}" />
                    <button type="submit" style="border-radius: 18px;width: auto"  class="btn btn-primary">Save</button>
                </div>
                <div class="form-group">
                    <img style="width: 14px;margin-left: 5px;" src="https://cdn3.iconfinder.com/data/icons/web-interface-6/10/star-red-512.png">
                    <span> – means that field is required</span>
                </div>
            </form>
        </div>
    </div>
</@p.page>