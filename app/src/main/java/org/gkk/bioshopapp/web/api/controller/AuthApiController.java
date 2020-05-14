package org.gkk.bioshopapp.web.api.controller;

import org.gkk.bioshopapp.service.model.user.UserRegisterServiceModel;
import org.gkk.bioshopapp.service.service.AuthService;
import org.gkk.bioshopapp.web.api.model.user.UserRegisterRequestModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class AuthApiController {
    private final AuthService authService;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthApiController(AuthService authService, ModelMapper modelMapper) {
        this.authService = authService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/api/register")
    @PreAuthorize("isAnonymous()")
    public void register(UserRegisterRequestModel model, BindingResult bindingResult, HttpServletResponse response) throws IOException {
        if (bindingResult.hasErrors()) {
            response.sendRedirect("/register");
            return;
        }

        UserRegisterServiceModel serviceModel = this.modelMapper.map(model, UserRegisterServiceModel.class);
        authService.register(serviceModel);
        response.sendRedirect("/login");
    }
}
