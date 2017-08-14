package com.ea.expresshire.controller;

import com.ea.expresshire.model.UserType;
import com.ea.expresshire.services.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by kcp on 8/13/17.
 */
@Controller
public class RootController {

//    @Autowired
//    EmailService emailService;

    @RequestMapping("/")
    public String redirectRoot() {
        //emailService.sendEmail("Text Message", "mohammed.ahmed.ps@gmail.com", "Test Email");
        return "index";
    }

    @RequestMapping("/index")
    public String index(){
        return "redirect:/";
    }

    @RequestMapping("/afterLogin")
    public String afterLogin(Model model, Authentication authentication, SecurityContextHolderAwareRequestWrapper request){
        if(request.isUserInRole(UserType.ROLE_ADMIN.name())){
            return "redirect:/admin";
        }else if(request.isUserInRole(UserType.ROLE_APPLICANT.name())){
            return "userProfile";
        }else{
            return "redirect:/jobPost";
        }
    }

}