<#import "../../macros/page.ftl" as p>
<@p.page>



<div style="margin: 8% 18% 2%;">
    <div style=" margin-bottom: 1.5%;border-radius:8px;width: 66.666667%;border:1px solid #e0e0e0;background-color: white">
        <div style="padding-left: 10px;font-size: 18px;margin: 10px;font-weight: 700;">
            Summary
            <a style="font-size: 15px;float: right;font-weight: 300;margin-right: 20px;" id="summary" href="#">Edit</a>
        </div>
        <div style="padding: 20px;">
            <div>
                <pre style="font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 400;">${developer.summary!}</pre>
            </div>
            <div id="summaryBox" style="padding-top: 20px;display: none;">
                <form method="post" action="/edit-developer/summary">
                    <textarea autofocus name="summary" style="border-radius: 8px" class="form-control" id="textArea" rows="3">${developer.summary!}</textarea>
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
            <div style="padding-left: 10px;font-size: 18px;margin: 10px;font-weight: 700;">Contact Information <a  style="font-size: 15px;float: right;font-weight: 300;margin-right: 20px;" href="#" id="contact"> Edit</a></div>
            <div id="contactBox" style="padding: 20px;display: none;">
            </div>
            <div style="padding-left: 20px; padding-bottom: 10px;" class="container">
                <strong style="line-height: 1.25rem;font-weight: 400;">${developer.email!}</strong>
                    <div style="font-size: 14px; color: #00c">
                        <a href="/edit-developer">Add phone number</a>
                    </div>
            </div>
        </div>
        <div style=" margin-bottom: 1.5%;border-radius:8px;width: 66.666667%;border:1px solid #e0e0e0;background-color: white">
            <div style="padding-left: 10px;font-size: 18px;margin: 10px;font-weight: 700;">
                Work Experience <a  style="font-size: 15px;float: right;font-weight: 300;margin-right: 20px;" href="/resume/wizard/experience">Add</a>
            </div>
            <div style="padding: 20px;">
                <#list developer.workExperiences as experience>
                   <div style="padding-top: 10px">
                       <a  style="font-size: 15px;float: right;font-weight: 300;margin-right: 20px;" id="workExperience" href="/edit-developer/experience/${experience.id}">Edit</a>
                       <strong>${experience.jobTitle}</strong><br>
                      <div style="padding-top: 1.4%;color: #767676;font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;font-size: 14px;letter-spacing: 0;line-height: 1.25rem;font-weight: 400;display: inline-block">
                          ${experience.company} - ${experience.city}<br>
                          ${experience.monthFrom} ${experience.yearFrom} to ${experience.monthTo} ${experience.yearTo}
                      </div>
                       <div style="padding-top: 20px;">
                           <pre style="font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 400;">${experience.description}</pre>
                        </div>
                        <div style="color: gainsboro">
                       ___________________________________________________________________________
                    </div>
                   </div>
                </#list>
        </div>
        </div>

    <div style=" margin-bottom: 1.5%;border-radius:8px;width: 66.666667%;border:1px solid #e0e0e0;background-color: white">
        <div style="padding-left: 10px;font-size: 18px;margin: 10px;font-weight: 700;">
            Education <a  style="font-size: 15px;float: right;font-weight: 300;margin-right: 20px;" href="/resume/wizard/education">Add</a>
        </div>
        <div style="padding: 20px;">
                    <#list developer.education as education>
                       <div style="padding-top: 10px">
                           <a  style="font-size: 15px;float: right;font-weight: 300;margin-right: 20px;" href="/edit-developer"> Edit</a>
                        <div style="font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 400;">
                            ${education.degree} in ${education.fieldOfStudy}<br>
                            ${education.place} - ${education.cityOfEducation}<br>
                            ${education.monthFrom} ${education.yearFrom} to ${education.monthTo} ${education.yearTo}
                        </div>
                        <div style="color: gainsboro">
                            ___________________________________________________________________________
                        </div>
                       </div>
                    </#list>
        </div>
    </div>

    <div style=" margin-bottom: 1.5%;border-radius:8px;width: 66.666667%;border:1px solid #e0e0e0;background-color: white">
        <div style="padding-left: 10px;font-size: 18px;margin: 10px;font-weight: 700;">
            Skills <a style="font-size: 15px;float: right;font-weight: 300;margin-right: 20px;" id="skills" href="#">Add</a>
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
                            <form method="post" action="/edit-developer/skill-delete/${specialization.id}">
                                <input type="submit" value="delete">
                                <#--<input type="image" src="https://image.flaticon.com/icons/svg/148/148777.svg" style="size:height: 1.5rem;width: 1.1rem;float: right;margin-right: 30px;">-->
                                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            </form>
                            <div style="font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 400;">
                                ${specialization.skill} (${specialization.experience})
                            </div>
                            <div style="color: gainsboro">
                                ___________________________________________________________________________
                            </div>
                        </div>
                    </#list>
        </div>
    </div>
    <div style=" margin-bottom: 1.5%;border-radius:8px;width: 66.666667%;border:1px solid #e0e0e0;background-color: white">
        <div style="padding-left: 10px;font-size: 18px;margin: 10px;font-weight: 700;">
            Additional information
            <a style="font-size: 15px;float: right;font-weight: 300;margin-right: 20px;" id="additional" href="#">Edit</a>
        </div>
        <div style="padding: 20px;">
            <div>
                <pre style="font-family: Helvetica Neue,Helvetica,Arial,Liberation Sans,Roboto,Noto,sans-serif;font-size: .875rem;letter-spacing: 0;line-height: 1.25rem;font-weight: 400;">${developer.additionalInformation!}</pre>
            </div>
            <div id="additionalBox" style="padding-top: 20px;display: none;">
                <form method="post" action="/edit-developer/additional">
                    <textarea autofocus name="additional" style="border-radius: 8px" class="form-control" id="textArea" rows="3">${developer.additionalInformation!}</textarea>
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
        }
    };
    function openBox(id, button) {
        var div = document.getElementById(id);
        if(div.style.display == 'block') {
            div.style.display = 'none';
            button.innerHTML = 'Edit';
        }
        else {
            div.style.display = 'block';
            button.innerHTML = 'Cancel';
        }
    }
</script>



</@p.page>