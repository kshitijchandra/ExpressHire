package com.ea.expresshire.controller;

import com.ea.expresshire.model.Applicant;
import com.ea.expresshire.model.Recruiter;
import com.ea.expresshire.model.UserType;
import com.ea.expresshire.services.applicant.ApplicantService;
import com.ea.expresshire.services.job.JobService;
import com.ea.expresshire.services.recruiter.RecruiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/recruiter")
public class RecruiterController {

    @Autowired
    RecruiterService recruiterService;
    @Autowired
    JobService jobService;
    @Autowired
    ApplicantService applicantService;

    @RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    //TODO: put @Valid.
    public void signUpPost(@RequestBody Recruiter recruiter) {
        //TODO: in the service layer, I have to check if the user is already exist or not.
        recruiter.setUserType(UserType.ROLE_RECRUITER);
        recruiterService.addNewRecruiter(recruiter);
    }
    @PreAuthorize("hasRole('ROLE_RECRUITER')")
    @RequestMapping(value = "/{recruiterId}", method = RequestMethod.PUT)
    //TODO: put @Valid.
    public void updateRecruiter(@PathVariable ("recruiterId") long recruiterId, Model model) {

    }
    @PreAuthorize("hasRole('ROLE_RECRUITER')")
    @RequestMapping("")
    public String profile(Model model, Principal principal){
        model.addAttribute("recruiterProfile", recruiterService.getRecruiterByEmail(principal.getName()));
        return "recruiter";
    }


    @PreAuthorize("hasRole('ROLE_RECRUITER')")
    @RequestMapping(value = "/approvedJobs",method = RequestMethod.POST)
    public String approvedJob( long applicantId,long job_id){
        jobService.getJob(job_id).setApprovedApplicant(applicantService.findApplicantById(applicantId));
        System.out.println(jobService.getJob(job_id).getApprovedApplicant().getFirstName());
        //System.out.println(jobService.getJob(job_id).getApplicants().remove(applicantService.findApplicantById(applicantId)));
        jobService.getJob(job_id).getApplicants().remove(applicantService.findApplicantById(applicantId));
        applicantService.findApplicantById(applicantId).deleteAppliedJob(jobService.getJob(job_id));
        jobService.getJob(job_id).removeApplicants(applicantService.findApplicantById(applicantId));
        applicantService.findApplicantById(applicantId).getAppliedJobs().remove(jobService.getJob(job_id));
        jobService.getJob(job_id).getApplicants().remove(applicantService.findApplicantById(applicantId));

        //applicantService.deleteApplicantById(applicantId);
        return "redirect:/recruiter";

    }
}
