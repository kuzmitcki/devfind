<#import "../macros/page.ftl" as p>
<#include "../macros/security.ftl">

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

    <div style="margin-left: 25%;margin-top: 10%;">
        <p style="margin-bottom: 0;width: 31%;font-size: 35px; color: #f60">Create an Indeed Resume.</p>
        <p style="margin-bottom: 0;color:#494949;font-size: 18px;line-height: 1.4;width:31%">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
        <a class="btn btn-primary" style="font-size: 24px; border-radius: 9px;" href="/resume/wizard/profile">Create</a>
        <img style="margin-left: 22%;width: 450px" src="https://hiring.careerbuilder.com/assets/jobs/jobs--progress-image.png">
    </div>
</@p.page>