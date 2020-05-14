package org.gkk.bioshopapp.web.api.controller;

import org.gkk.bioshopapp.service.model.user.UserEditProfileServiceModel;
import org.gkk.bioshopapp.service.model.user.UserProfileServiceModel;
import org.gkk.bioshopapp.service.service.UserService;
import org.gkk.bioshopapp.web.api.model.user.UserEditProfileRequestModel;
import org.gkk.bioshopapp.web.api.model.user.UserProfileResponseModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserApiController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserApiController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/all-users")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ResponseEntity<List<UserProfileResponseModel>> getAllUsers() {
        List<UserProfileResponseModel> users = this.userService.getAllUsers().stream()
                .map(userService -> this.modelMapper.map(userService, UserProfileResponseModel.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserProfileResponseModel> getProfile(HttpSession session) throws Exception {
        String username = session.getAttribute("username").toString();
        UserProfileServiceModel serviceModel = this.userService.getUserByUsername(username);
        UserProfileResponseModel user = this.modelMapper.map(serviceModel, UserProfileResponseModel.class);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/profile/edit")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserProfileResponseModel> getEditProfile(HttpSession session) throws Exception {
        String username = session.getAttribute("username").toString();
        UserProfileServiceModel serviceModel = this.userService.getUserByUsername(username);
        UserProfileResponseModel user = this.modelMapper.map(serviceModel, UserProfileResponseModel.class);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/profile/edit")
    @PreAuthorize("isAuthenticated()")
    public void editProfileConfirm(UserEditProfileRequestModel model, HttpServletResponse response) throws IOException {
        UserEditProfileServiceModel serviceModel = this.modelMapper.map(model, UserEditProfileServiceModel.class);

        try {
            this.userService.editUserProfile(serviceModel);
            response.sendRedirect("/user/profile");
        } catch (Exception e) {
            response.sendRedirect("/user/profile/edit");
        }
    }

    @PostMapping("/set-admin/{id}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public void setAdminRole(@PathVariable String id, HttpServletResponse response) throws IOException {
        this.userService.makeAdmin(id);
        response.sendRedirect("/user/all-users");
    }

    @PostMapping("/set-user/{id}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public void setUserRole(@PathVariable String id, HttpServletResponse response) throws IOException {
        this.userService.makeUser(id);
        response.sendRedirect("/user/all-users");
    }
}
