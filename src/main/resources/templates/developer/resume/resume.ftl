<#import "../../macros/page.ftl" as p>
<#include "../../macros/security.ftl">
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

    ${message!}

<div style="margin-left: 25% !important; margin: 8% 13% 8%;">
    <div style=" margin-bottom: 1.5%;border-radius:8px;width: 66.666667%;border:1px solid #e0e0e0;background-color: white">
        <div style="padding-left: 10px;font-size: 18px;margin: 10px;font-weight: 700;">
            Summary
            <a style="font-size: 15px;float: right;font-weight: 300;margin-right: 30px;" id="summary" href="#"><input type="image" src="https://image.flaticon.com/icons/svg/660/660756.svg" style="height: 1.1rem;width: 1.5rem;float: right"></a>
        </div>
        <div style="padding-top: 11px;padding-left: 20px;">
            <div>
                <pre class="more" style="margin-right: 10px;font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 400;">${developer.summary!}</pre>
            </div>
            <div id="summaryBox" style="padding-top: 20px;display: none;">
                <form method="post" action="/edit-developer/summary">
                    <textarea autofocus name="summary" style="height:260px;border-radius: 8px;font-size: 13px" class="form-control" id="textArea" rows="3">${developer.summary!}</textarea>
                    <div class="form-group">
                        <input type="hidden" name="_csrf" value="${_csrf.token}" />
                        <button type="submit" style="border-radius: 18px;  margin-top: 10px;" class="btn btn-primary">Save</button>
                        <a style="border-radius: 18px;  margin-top: 10px;" class="btn btn-primary" id="summary" href="">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div style="margin-bottom: 1.5%;border-radius:8px;width: 66.666667%;border:1px solid #e0e0e0;background-color: white">
        <div style="padding-left: 10px;font-size: 18px;margin: 10px;font-weight: 700;">Contact Information
            <a style="font-size: 15px;float: right;font-weight: 300;margin-right: 30px;" id="contact" href="#"><input type="image" src="https://image.flaticon.com/icons/svg/660/660756.svg" style="height: 1.1rem;width: 1.5rem;float: right"></a>
        </div>
        <div id="contactBox" style="padding: 20px;display: none;">
            <form method="post" action="/edit-developer/information">
                <div class="form-group">
                    <label  for="inputCountry" style="color: #4b4b4b;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 700;display: block;">Country (optional)
                    </label>
                    <input value="${developer.country!}"  style="border-radius: 8px;" type="text" class="form-control" name="country" id="inputCountry">
                </div>
                <div class="form-group">
                    <label  for="inputCity" style="color: #4b4b4b;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 700;display: block;">City (optional)</label>
                    <input  value="${developer.city!}"  style="border-radius: 8px;" type="text" class="form-control" name="city" id="inputCity">
                </div>
                <div class="form-group">
                    <label  for="inputTelephone" style="color: #4b4b4b;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 700;display: block;">Telephone (optional)</label>
                    <input value="${developer.telephone!}"   style="border-radius: 8px;" type="text" class="form-control" name="telephone" id="inputTelephone">
                </div>
                <div class="form-group">
                    <label  for="zipPostalCode" style="color: #4b4b4b;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 700;display: block;">Postal code (optional)</label>
                    <input value="${developer.zipPostalCode!}"  style="border-radius: 8px;" type="text" class="form-control" name="zipPostalCode" id="zipPostalCode">
                </div>
                <div class="form-group">
                    <label  for="inputEmail" style="color: #4b4b4b;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 700;display: block;">Email (optional)</label>
                    <input value="${user.email!}" required  style="border-radius: 8px;" type="text" class="form-control" name="email" id="inputEmail">
                </div>
                <div style="font-size: 0.85rem" class="form-group">
                    <input type="hidden" name="_csrf" value="${_csrf.token}" />
                    <button type="submit" style="border-radius: 18px;  margin-top: 10px;" class="btn btn-primary">Save</button>
                    <a style="border-radius: 18px;  margin-top: 10px;" class="btn btn-primary" id="contact" href="">Cancel</a>
                </div>
            </form>
        </div>
        <div style="padding-left: 20px; padding-bottom: 10px;" class="container">
            <strong style="line-height: 1.25rem;font-weight: 400;">${user.email!}</strong>
                    <#if user.developer.telephone??>
                     <div style="font-size: 14px; color: #767676">
                         Telephone: ${user.developer.telephone}
                     </div>
                    <#else>
                    <div style="font-size: 14px; color: #00c">
                        <a id="contact" href="#">Add phone number</a>
                    </div>
                    </#if>
        </div>
    </div>
    <div style="margin-bottom: 1.5%;border-radius:8px;width: 66.666667%;border:1px solid #e0e0e0;background-color: white">
        <div style="padding-left: 10px;font-size: 18px;margin-top: 10px;margin-left: 10px;font-weight: 700;">Desired Job<a  style="font-size: 15px;float: right;font-weight: 300;margin-right: 39px;" href="#" id="desired"> <input type="image" src="https://image.flaticon.com/icons/svg/660/660756.svg" style="height: 1.1rem;width: 1.5rem;float: right"></a></div>
        <div style="padding-left: 20px" class="container">
            <pre style="font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 400;">Help us match you with your next job</pre>
        </div>
        <div style="padding: 20px;display: none" id="desiredBox">
            <form action="/edit-developer/desired" method="post">
                <div class="form-group">
                    <label for="inputTitle" style="color: #000;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 700;display: block;">
                        Desired Job Title
                    </label>
                    <input value="<#if developer.desiredJob??>${developer.desiredJob.desiredJobTitle!}<#else></#if>" style="border-radius: 8px;" type="text" class="form-control" name="title" id="inputTitle">
                </div>
                <div class="form-group">
                    <label for="inputJobType" style="color: #000;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 700;display: block;">
                        Desired Job Types
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
                <div class="form-row">
                    <span style="padding: 4.5% 0 0;">$</span>
                    <div class="form-group col-md-6">
                        <label for="fromSalary" style="margin-left: 7px;color: #000;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 700;display: block;">
                            Desired Salary
                        </label>
                        <input pattern="^[ 0-9]+$" value="<#if developer.desiredJob??>${developer.desiredJob.desiredSalary!}<#else></#if>"  placeholder="Example: 50,000" style="margin-left: 7px;border-radius: 8px" type="text" class="form-control" name="salary" id="fromSalary">
                    </div>
                    <div class="form-group col-md-3">
                        <select name="salaryPeriod" style="margin-top: 28px;margin-left: 20px;width: 175%; border-radius: 8px" id="inputPeriod" class="custom-select">
                            <option selected>per year</option>
                            <option>per month</option>
                            <option>per week</option>
                            <option>per day</option>
                            <option>per hour</option>
                        </select>
                    </div>
                </div>
                <div style="font-size: 0.85rem" class="form-group">
                    <input type="hidden" name="_csrf" value="${_csrf.token}" />
                    <button type="submit" style="border-radius: 18px;  margin-top: 10px;" class="btn btn-primary">Save</button>
                    <a style="border-radius: 18px;  margin-top: 10px;" class="btn btn-primary" id="contact" href="">Cancel</a>
                </div>
            </form>
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

    </div>



    <div style=" margin-bottom: 1.5%;border-radius:8px;width: 66.666667%;border:1px solid #e0e0e0;background-color: white">
        <div style="padding-left: 10px;font-size: 18px;margin: 10px;font-weight: 700;">
            Work Experience <a  style="font-size: 15px;float: right;font-weight: 300;margin-right: 30px;" href="/resume/wizard/experience"><input type="image" src="https://image.flaticon.com/icons/svg/969/969621.svg" style="height: auto;width: 1.8rem;float:"></a>
        </div>
        <div style="padding: 20px;">
                <#list developer.workExperiences as experience>
                    <div style="padding-top: 10px">
                        <a  style="font-size: 15px;float: right;font-weight: 300;margin-right: 20px;" id="workExperience" href="/edit-developer/experience/${experience.id!}"><input type="image" src="https://image.flaticon.com/icons/svg/660/660756.svg" style="height: 1.1rem;width: 1.5rem;float:"></a>
                        <form method="post" action="/edit-developer/work-delete/${experience.id!}">
                            <input type="image" src="https://image.flaticon.com/icons/svg/148/148777.svg" style="height: 1.1rem;width: 1.5rem;float: right;margin-right: 15px;">
                            <input type="hidden" name="_csrf" value="${_csrf.token}" />
                        </form>
                        <strong>${experience.jobTitle}</strong><br>
                        <div style="padding-top: 1.4%;color: #767676;font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;font-size: 14px;letter-spacing: 0;line-height: 1.25rem;font-weight: 400;display: inline-block">
                            ${experience.company} - ${experience.city}<br>
                            ${experience.monthFrom} ${experience.yearFrom} to ${experience.monthTo} ${experience.yearTo}
                        </div>
                        <div style="padding-top: 20px;">
                            <pre class="more" style="font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 400;">${experience.description}</pre>
                        </div>
                        <hr style="width:100%;font-weight: 600;color: gainsboro">
                    </div>
                </#list>
        </div>
    </div>

    <div style=" margin-bottom: 1.5%;border-radius:8px;width: 66.666667%;border:1px solid #e0e0e0;background-color: white">
        <div style="padding-left: 10px;font-size: 18px;margin: 10px;font-weight: 700;">
            Education <a  style="font-size: 15px;float: right;font-weight: 300;margin-right: 30px;" href="/resume/wizard/education"><input type="image" src="https://image.flaticon.com/icons/svg/969/969621.svg" style="size:height: auto;width: 1.8rem;float:"></a>
        </div>
        <div style="padding: 20px;">
                    <#list developer.education as education>
                        <div style="padding-top: 10px">
                            <a  style="font-size: 15px;float: right;font-weight: 300;margin-right: 20px;" href="/edit-developer/education/${education.id!}"><input type="image" src="https://image.flaticon.com/icons/svg/660/660756.svg" style="height: 1.1rem;width: 1.5rem;float:"></a>
                            <form method="post" action="/edit-developer/education-delete/${education.id!}">
                                <input type="image" src="https://image.flaticon.com/icons/svg/148/148777.svg" style="height: 1.1rem;width: 1.5rem;float: right;margin-right: 15px;">
                                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                            </form>
                            <div style="font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 400;">
                                ${education.degree} in ${education.fieldOfStudy}<br>
                                ${education.place} - ${education.cityOfEducation}<br>
                                ${education.monthFrom} ${education.yearFrom} to ${education.monthTo} ${education.yearTo}
                            </div>
                            <hr style="width:100%;font-weight: 600;color: gainsboro">
                        </div>
                    </#list>
        </div>
    </div>

    <div style=" margin-bottom: 1.5%;border-radius:8px;width: 66.666667%;border:1px solid #e0e0e0;background-color: white">
        <div style="padding-left: 10px;font-size: 18px;margin: 10px;font-weight: 700;">
            Skills <a style="font-size: 15px;float: right;font-weight: 300;margin-right: 30px;" id="skills" href="#"><input type="image" src="https://image.flaticon.com/icons/svg/969/969621.svg" style="height: auto;width: 1.8rem;float:"></a>
        </div>
        <div id="skillsBox"  style="padding: 20px;display: none;">
            <form method="post" action="/edit-developer/skills">
                <div class="form-group">
                    <label  for="inputSkill" style="color: #4b4b4b;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 700;display: block;">Skill
                        <div>
                            <span style="color: #767676;font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;font-size: .75rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 400;display: inline-block">e.g. Microsoft Office.</span>
                        </div>
                    </label>
                    <input required  style="border-radius: 8px;" type="text" class="form-control" name="skill" id="inputSkill">
                </div>
                <div class="form-group">
                    <label  for="inputExperience" style="color: #4b4b4b;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 700;display: block;">Experience</label>
                    <select required style="color: #4b4b4b; border-radius: 8px;font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;font-size: .875rem;" class="custom-select" id="inputExperience" name="experience">
                        <option>-----</option>
                        <option>Less than 1 year</option>
                        <option>1 year</option>
                        <option>2 years</option>
                        <option>3 years</option>
                        <option>4 years</option>
                        <option>5 years</option>
                        <option>6 years</option>
                        <option>7 years</option>
                        <option>8 years</option>
                        <option>9 years</option>
                        <option>10 years</option>
                        <option>11 years</option>
                    </select>
                </div>
                <div style="font-size: 0.85rem" class="form-group">
                    <input type="hidden" name="_csrf" value="${_csrf.token}" />
                    <button type="submit" style="border-radius: 18px;  margin-top: 10px;" class="btn btn-primary">Save</button>
                    <a style="border-radius: 18px;  margin-top: 10px;" class="btn btn-primary" id="skills" href="">Cancel</a>
                </div>
            </form>
        </div>
        <div style="padding: 20px;">
                    <#list developer.specializations as specialization>
                        <div style="padding-top: 10px">
                            <a  style="font-size: 15px;float: right;font-weight: 300;margin-right: 20px;" href="/edit-developer/skill/${specialization.id!}"><input type="image" src="https://image.flaticon.com/icons/svg/660/660756.svg" style="height: 1.1rem;width: 1.5rem;float:"></a>
                            <form method="post" action="/edit-developer/skill-delete/${specialization.id!}">
                                <input type="image" src="https://image.flaticon.com/icons/svg/148/148777.svg" style="height: 1.1rem;width: 1.5rem;float: right;margin-right: 15px;">
                                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            </form>
                            <div style="font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 400;">
                                ${specialization.skill} (${specialization.experience})
                            </div>
                            <hr style="width:100%;font-weight: 600;color: gainsboro">
                        </div>
                    </#list>
        </div>
    </div>
    <div style=" margin-bottom: 1.5%;border-radius:8px;width: 66.666667%;border:1px solid #e0e0e0;background-color: white">
        <div style="padding-left: 10px;font-size: 18px;margin: 10px;font-weight: 700;">
            Additional information
            <a style="font-size: 15px;float: right;font-weight: 300;margin-right: 30px;" id="additional" href="#"><input type="image" src="https://image.flaticon.com/icons/svg/660/660756.svg" style="height: 1.1rem;width: 1.5rem;float: right"></a>
        </div>
        <div style="padding: 20px;">
            <div>
                <pre  class="more" style="font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 400;">${developer.additionalInformation!}</pre>
            </div>
            <div id="additionalBox" style="padding-top: 20px;display: none;">
                <form method="post" action="/edit-developer/additional">
                    <textarea autofocus name="additional" style="height:260px;border-radius: 8px;font-size: 13px" class="form-control" id="textArea" rows="3">${developer.additionalInformation!}</textarea>
                    <div class="form-group">
                        <input type="hidden" name="_csrf" value="${_csrf.token}" />
                        <button type="submit" style="border-radius: 18px;  margin-top: 10px;" class="btn btn-primary">Save</button>
                        <a style="border-radius: 18px;  margin-top: 10px;" class="btn btn-primary" id="additional" href="">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </div>

</div>




<script>
    window.onload= function() {
        document.getElementById("additional").onclick = function () {
            openBox("additionalBox", this);
            return false;
        };
        document.getElementById('summary').onclick = function() {
            openBox('summaryBox', this);
            return false;
        };
        document.getElementById("skills").onclick = function () {
            openBox("skillsBox", this);
            return false;
        };
        document.getElementById("contact").onclick = function () {
            openBox("contactBox", this);
            return false;
        };
        document.getElementById("workExperience").onclick = function (ev) {
            openBox("workExperienceBox", this);
            return false;
        };
        document.getElementById("desired").onclick = function (ev) {
            openBox("desiredBox", this);
            return false;
        }
    };
    function openBox(id, button) {
        var div = document.getElementById(id);
        if(div.style.display === 'block') {
            div.style.display = 'none';
            button.innerHTML = 'Edit';
        }
        else {
            div.style.display = 'block';
            button.innerHTML = 'Cancel';
        }
    }
</script>

<script>
    $(document).ready(function() {
        // Configure/customize these variables.
        var showChar = 500;  // How many characters are shown by default
        var ellipsestext = "...";
        var moretext = "Show less";
        var lesstext = "Show more";


        $('.more').each(function() {
            var content = $(this).html();

            if(content.length > showChar) {

                var c = content.substr(0, showChar);
                var h = content.substr(showChar, content.length - showChar);

                var html = c + '<span class="moreellipses">' + ellipsestext+ '&nbsp;</span><span class="morecontent"><span>' + h + '</span>&nbsp;&nbsp;<a href="" class="morelink">' + moretext + '</a></span>';

                $(this).html(html);
            }

        });

        $(".morelink").click(function(){
            if($(this).hasClass("less")) {
                $(this).removeClass("less");
                $(this).html(moretext);
            } else {
                $(this).addClass("less");
                $(this).html(lesstext);
            }
            $(this).parent().prev().toggle();
            $(this).prev().toggle();
            return false;
        });
    });
</script>



</@p.page>