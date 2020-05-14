package org.gkk.bioshopapp.web.view.controller;

import org.gkk.bioshopapp.web.annotation.PageTitle;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthController extends BaseController {

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    @PageTitle("Register")
    public ModelAndView getRegisterForm() {
        return super.view("user/register");
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    @PageTitle("Login")
    public ModelAndView getLoginForm(@RequestParam(required = false) String error) {
        ModelAndView model = new ModelAndView("user/login");

        if (error != null) {
            model.addObject("error", error);
        }

        return model;
    }
}
