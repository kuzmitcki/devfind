<#import "../../macros/page.ftl" as p>
<#import "../../macros/search.ftl" as s>
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
        </ul>
        <div class="navbar-text mr-3">${email}</div>
        <form method="post" action="/logout">
            <input style="border-radius: 9px" class="btn btn-primary" type="submit" value="Sign out">
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
        </form>
    </div>
</nav>
    <div style="text-align: center;margin-bottom: 2em">
        <h3>Advanced Resume Search</h3>
    </div>
        <div class="container h-100 " style=" font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;">

            <div class="row h-100 justify-content-center align-items-center">
                <form style="padding: 30px;" class="col-5"  method="post" action="/resume/search/advanced">
                    <div style="color: #000;font-weight: 700; margin-top: -5.5%; margin-left: -43%" >
                        Find resumes with...
                    </div>
                    <div class="form-group">
                        <div    class="form-group">
                            <label style="font-size: 0.875rem;" for="allWordsInput">
                                All of these words
                            </label>
                            <input  required name="allWords" style="width: 145%;border-radius: 8px" id="allWordsInput" type="text" class="form-control" >
                        </div>
                        <div  class="form-group">
                            <label style="font-size: 0.875rem;" for="phraseInput">
                                The exact phrase
                            </label>
                            <input required name="phrase" style="width: 145%;border-radius: 8px" id="phraseInput" type="text" class="form-control" >
                        </div>
                        <div  class="form-group">
                            <label style="font-size: 0.875rem;" for="wordInput">
                                At least one of these words
                            </label>
                            <input required name="oneWord" style="width: 145%;border-radius: 8px" id="wordInput" type="text" class="form-control" >
                        </div>
                    </div>
                    <div style="color: gainsboro; margin-left: -44%;padding-bottom: 2rem">
                        _______________________________________________________________________________________
                    </div>
                    <div style="color: #000;font-weight: 700; margin-top: -5.5%; margin-left: -43%" >
                        Work Experience
                    </div>
                    <div class="form-group">
                        <div    class="form-group">
                            <label style="font-size: 0.875rem;" for="titleInput">
                                Title of any job
                            </label>
                            <input name="title" style="width: 145%;border-radius: 8px" id="titleInput" type="text" class="form-control" >
                        </div>
                        <div  class="form-group">
                            <label style="font-size: 0.875rem;" for="companyInput">
                                Company of any job
                            </label>
                            <input name="company" style="width: 145%;border-radius: 8px" id="companyInput" type="text" class="form-control" >
                        </div>
                        <div  class="form-group">
                            <label style="font-size: 0.875rem;" for="experienceInput">
                                Years of Work Experience
                            </label>
                            <select id="experienceInput" style="width: 145%;border-radius: 8px" name="experience"  class="custom-select">
                                <option value="15" selected>Any amount</option>
                                <option value="0" >Less than 1 year</option>
                                <option value="1" >1-2 years</option>
                                <option value="3" >3-5 years</option>
                                <option value="6" >6-10 years</option>
                                <option value="10" >More than 10 years</option>
                            </select>
                        </div>
                    </div>
                    <div style="color: gainsboro; margin-left: -44%;padding-bottom: 2rem">
                        _______________________________________________________________________________________
                    </div>
                    <div style="color: #000;font-weight: 700; margin-top: -5.5%; margin-left: -43%" >
                        Education
                    </div>
                    <div class="form-group">
                        <div    class="form-group">
                            <label style="font-size: 0.875rem;" for="placeInput">
                                School name
                            </label>
                            <input name="place" style="width: 145%;border-radius: 8px" id="placeInput" type="text" class="form-control" >
                        </div>
                        <div  class="form-group">
                            <label style="font-size: 0.875rem;" for="degreeInput">
                                Degree
                            </label>
                            <select id="degreeInput"  style="width: 145%;border-radius: 8px" name="degree"  class="custom-select">
                                <option selected value="1">Any degree</option>
                                <option value="Diploma">Diploma</option>
                                <option value="Associates">Associates</option>
                                <option value="Bachelors">Bachelors</option>
                                <option value="Masters">Masters</option>
                                <option value="Doctorate">Doctorate</option>
                            </select>
                        </div>
                        <div  class="form-group">
                            <label style="font-size: 0.875rem;" for="fieldInput">
                                Field of study
                            </label>
                            <input name="field" style="width: 145%;border-radius: 8px" id="fieldInput" type="text" class="form-control" >
                        </div>
                        <div style="color: gainsboro; margin-left: -44%;padding-bottom: 2rem">
                            _______________________________________________________________________________________
                        </div>
                        <div style="color: #000;font-weight: 700; margin-top: -5.5%; margin-left: -43%" >
                            Location
                        </div>
                        <div class="form-group">
                            <div    class="form-group">
                                <label style="font-size: 0.875rem;" for="locationInput">
                                    City, state, or zip
                                </label>
                                <input name="location" style="width: 145%;border-radius: 8px" id="locationInput" type="text" class="form-control" >
                            </div>
                        </div>
                        <div class="form-group">
                            <input type="submit" style="margin-top:3rem;border-radius: 6.25rem; width: 121px; height: 44px"  class="btn btn-primary" value="Find resumes">
                            <input type="hidden" name="_csrf" value="${_csrf.token}" />
                        </div>
                </form>
            </div>
        </div>

</@p.page>