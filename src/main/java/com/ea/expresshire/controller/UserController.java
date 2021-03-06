package com.ea.expresshire.controller;

import com.ea.expresshire.exception.UserNotAuthenticatedException;
import com.ea.expresshire.exception.UserNotFoundException;
import com.ea.expresshire.model.Applicant;
import com.ea.expresshire.model.Recruiter;
import com.ea.expresshire.model.User;
import com.ea.expresshire.model.UserType;
import com.ea.expresshire.services.applicant.ApplicantService;
import com.ea.expresshire.services.recruiter.RecruiterService;
import com.ea.expresshire.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RecruiterService recruiterService;

    @Autowired
    ApplicantService applicantService;

    @RequestMapping(value = "/login")
    public void login(@RequestBody @Valid User user) throws UserNotFoundException, UserNotAuthenticatedException{

        userService.login(user);
        //now, we are sure that user is authenticated, so we need to add something in the session.

    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    //TODO: put @Valid.
    public String getUser(@PathVariable("userId") long userId, Model model) throws UserNotFoundException{
        User user = userService.findUserById(userId);
        //TODO: we can get the id using pricipal object. (it is email).
        if(user.getUserType() == UserType.ROLE_RECRUITER) {
            //TODO: I have to check if the recruiter contains all attributes or not.
            // (I mean including the attributes inside the User class.
            Recruiter recruiter = recruiterService.findRecruiterById(userId);
            model.addAttribute("recruiter", recruiter);
            return "recruiter_profile";
        }
        else {
            Applicant applicant = applicantService.findApplicantById(userId);
            model.addAttribute("applicant", applicant);
            return "applicant_profile";
        }
    }

}
