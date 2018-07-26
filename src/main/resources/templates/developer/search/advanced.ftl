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

    <div style="text-align: center;padding-top: 5%;margin-bottom: 2em">
        <h3>Advanced Job Search</h3>
    </div>
    <div class="container h-100 " style=" font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;">

        <div class="row h-100 justify-content-center align-items-center">
            <form style="padding: 30px;" class="col-5"  method="post" action="/job/search/advanced">
                <div style="color: #000;font-weight: 700; margin-top: -5.5%; margin-left: -28%" >
                    Find Jobs
                </div>
                <div class="form-group">
                    <div    class="form-group">
                        <label style="font-size: 0.875rem;" for="allWordsInput">
                            With all of these words
                        </label>
                        <input required name="allWords" style="width: 145%;border-radius: 8px" id="allWordsInput" type="text" class="form-control" >
                    </div>
                    <div  class="form-group">
                        <label style="font-size: 0.875rem;" for="phraseInput">
                            With the exact phrase
                        </label>
                        <input required name="phrase" style="width: 145%;border-radius: 8px" id="phraseInput" type="text" class="form-control" >
                    </div>
                    <div  class="form-group">
                        <label style="font-size: 0.875rem;" for="wordInput">
                            With at least one of these words
                        </label>
                        <input required name="oneWord" style="width: 145%;border-radius: 8px" id="wordInput" type="text" class="form-control" >
                    </div>
                    <div  class="form-group">
                        <label style="font-size: 0.875rem;" for="wordInput">
                            With these words in the title
                        </label>
                        <input  name="title" style="width: 145%;border-radius: 8px" id="wordInput" type="text" class="form-control" >
                    </div>
                    <div class="form-group">
                        <label style="font-size: 0.875rem;" for="inputJobType">
                            Show jobs of type
                        </label>
                        <select   style="width: 145%;border-radius: 8px" class="custom-select" id="inputJobType" name="jobType">
                            <option value="All job types">All job types</option>
                            <option value="Full-time">Full-time</option>
                            <option value="Part-time">Part-time</option>
                            <option value="Temporary">Temporary</option>
                            <option value="Contract">Contract</option>
                            <option value="Internship">Internship</option>
                            <option value="Commision">Commision</option>
                        </select>
                    </div>
                    <label style="padding-left:10%;font-size: 0.875rem;" for="inputJobType">
                        Salary estimate
                    </label>
                    <div class="form-row">
                        <div style="text-align: center; padding-top: 2%" class="form-group col-md-1">
                            $
                        </div>
                        <div class="form-group col-md-8"  >
                            <input  name="salary" style="width: 110%;border-radius: 8px" id="wordInput" type="text" class="form-control" >
                        </div>
                        <div style="padding-left: 9%;text-align: center;padding-top: 1%;" class="form-group col-md-3">
                            <span>per year</span>
                        </div>
                    </div>
                    <div class="form-group">
                        <input type="hidden" name="_csrf" value="${_csrf.token}" />
                        <button type="submit" style="border-radius: 18px;width: auto"  class="btn btn-primary">Find jobs</button>
                    </div>
                </div>

            </form>
        </div>
    </div>

</@p.page>