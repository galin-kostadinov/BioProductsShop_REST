package org.gkk.bioshopapp.web.view.controller;

import org.gkk.bioshopapp.web.annotation.PageTitle;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Profile")
    public ModelAndView getProfile() {
        return super.view("user/profile");
    }

    @GetMapping("profile/edit")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Edit Profile")
    public ModelAndView getEditProfile() {
        return super.view("user/edit-profile");
    }

    @GetMapping("profile/delete")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Edit Profile")
    public ModelAndView getDeleteProfile() {
        return super.view("user/delete-profile");
    }

    @GetMapping("/all-users")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    @PageTitle("Users")
    public ModelAndView getAllUsers() {
        return super.view("user/all-users");
    }
}
