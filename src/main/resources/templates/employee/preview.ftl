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
<div style="margin-left: 25% !important; margin: 8% 13% 8%;" xmlns="http://www.w3.org/1999/html">
    <div style="margin-bottom: 1.5%;border-radius:8px;width: 66.666667%;border:1px solid #e0e0e0;background-color: white">
        <div style="padding-top: 4%;padding-bottom:4%;padding-left: 10px;font-size: 15px;margin: 10px;font-weight: 700;">
            <span style=" font-weight: 400; color: black;"> ${developer.firstName!} ${developer.lastName!}</span></br>
            <span style=" font-weight: 400; color: black;">${developer.country!}, ${developer.city!}</span></br>
            <span style=" font-weight: 400; color: black;">${developer.user.email!}</span></br>
            <span style=" font-weight: 400; color: black;">Status: <#if enable??>Waiting for job offers<#else>Has work</#if></span></br>
            <span style=" font-weight: 400; color: red;">${message!}</span>
        </div>
        <div style="padding-left: 10px;font-size: 18px;margin: 10px;font-weight: 700;">
            Summary <hr style="width: auto;font-weight: 600;color: gainsboro;margin-top: -1%;margin-left: 12%;margin-right: 6%;;">
        </div>
        <div style="padding-top: 11px;padding-left: 20px;">
            <div>
                <pre style="padding-right: 10px;font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 400;">${developer.summary!}</pre>
            </div>
        </div>
        <div style="padding-left: 10px;font-size: 18px;margin-top: 10px;margin-left: 10px;font-weight: 700;">Desired Job
            <hr style="margin-right: 6%;width: auto;font-weight: 600;color: gainsboro;margin-top: -1%;margin-left: 15%;">
        </div>
        <#if developer.desiredJob??>
            <div style="padding-left: 10px;font-size: 14px;margin-bottom: 20px;margin-top: 10px;margin-left: 10px;font-weight: 700;">
                <strong style="color: black;line-height: 1.25rem;font-weight: 600;">Desired Job Title</strong>
                <div style="font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 400;">
                    ${developer.desiredJob.desiredJobTitle!}
                </div>
            </div>
        <div style="padding-left: 10px;font-size: 14px;margin-bottom: 20px;margin-top: 10px;margin-left: 10px;font-weight: 700;">
            <strong style="line-height: 1.25rem;font-weight: 600;">Desired Job Type</strong>
            <div style="font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 400;">
                ${developer.desiredJob.desiredJobType!}
            </div>
        </div>
        <div style="padding-left: 10px;font-size: 14px;margin-bottom: 20px;margin-top: 10px;margin-left: 10px;font-weight: 700;">
            <strong style="line-height: 1.25rem;font-weight: 600;">Desired Salary</strong>
            <div style="font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 400;">
                $ ${developer.desiredJob.desiredSalary!} ${developer.desiredJob.desiredSalaryPeriod!}
            </div>
        </div>
        </#if>
        <div style="padding-left: 10px;font-size: 18px;margin: 10px;font-weight: 700;">
            Work Experience<hr style="margin-right: 4.5%;width: auto;font-weight: 600;color: gainsboro;margin-top: -1%;margin-left: 21%;">
        </div>
        <div style="padding: 20px;">
                <#list developer.workExperiences as experience>
                    <div style="padding-top: 10px">
                        <strong>${experience.jobTitle}</strong><br>
                        <div style="padding-top: 1.4%;color: #767676;font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;font-size: 14px;letter-spacing: 0;line-height: 1.25rem;font-weight: 400;display: inline-block">
                            ${experience.company} - ${experience.city}<br>
                            ${experience.monthFrom} ${experience.yearFrom} to ${experience.monthTo} ${experience.yearTo}
                        </div>
                        <div style="padding-top: 20px;">
                            <pre style="font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 400;">${experience.description}</pre>
                        </div>
                        <hr style="width:100%;font-weight: 600;color: gainsboro">
                    </div>
                </#list>
        </div>
        <div style="padding-left: 10px;font-size: 18px;margin: 10px;font-weight: 700;">
            Education  <hr style="margin-right: 6%;width: auto;font-weight: 600;color: gainsboro;margin-top: -1%;margin-left: 13%;">
        </div>
        <div style="padding: 20px;">
                    <#list developer.education as education>
                        <div style="padding-top: 10px">
                            <div style="font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 400;">
                                ${education.degree} in ${education.fieldOfStudy}<br>
                                ${education.place} - ${education.cityOfEducation}<br>
                                ${education.monthFrom} ${education.yearFrom} to ${education.monthTo} ${education.yearTo}
                            </div>
                            <hr style="width: 100%;font-weight: 600;color: gainsboro">
                        </div>
                    </#list>
        </div>
        <div style="padding-left: 10px;font-size: 18px;margin: 10px;font-weight: 700;">
            Skills  <hr style="margin-right: 6%;width: auto;font-weight: 600;color: gainsboro;margin-top: -1%;margin-left: 7%;">
        </div>
        <div style="padding: 20px;">
                    <#list developer.specializations as specialization>
                        <div style="padding-top: 10px">
                            <div style="font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 400;">
                                ${specialization.skill} (${specialization.experience})
                            </div>
                            <hr style="width: 100%;font-weight: 600;color: gainsboro">
                        </div>
                    </#list>
        </div>
        <div style="padding-left: 10px;font-size: 18px;margin: 10px;font-weight: 700;">
            Additional information  <hr style="margin-left: 27%;margin-right: 6%;width: auto;font-weight: 600;color: gainsboro;margin-top: -1%">
        </div>
        <div style="padding: 20px;">
            <div>
                <pre style="font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 400;">${developer.additionalInformation!}</pre>
            </div>
        </div>
        <#if enable??>
            <form style="margin: 3%" method="post" action="/resume/offer/${developer.id}">
                <input type="submit" style="border-radius: 6.25rem; width: 121px; height: 44px"  class="btn btn-primary" value="Reserve">
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
            </form>
        <#else>
        </#if>
    </div>
</@p.page>