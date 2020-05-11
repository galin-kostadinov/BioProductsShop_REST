package org.gkk.bioshopapp.web.view.controller;

import org.gkk.bioshopapp.web.annotation.PageTitle;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class HomeController extends BaseController {

    @GetMapping("/")
    @PreAuthorize("isAnonymous()")
    @PageTitle("Index")
    public ModelAndView index(ModelAndView model, Principal principal) {

        if (principal != null) {
            return super.redirect("/home");
        }

        return super.view("index", model);
    }

    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Home")
    public ModelAndView home(ModelAndView model) {
        return super.view("home", model);
    }

    @GetMapping("/contacts")
    @PageTitle("Contacts")
    public ModelAndView contact() {
        return super.view("contacts");
    }
}
