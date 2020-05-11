package org.gkk.bioshopapp.web.api.controller;

import org.gkk.bioshopapp.service.model.user.UserProfileServiceModel;
import org.gkk.bioshopapp.service.service.UserService;
import org.gkk.bioshopapp.web.api.model.UserProfileResponseModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
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
    public ResponseEntity<List<UserProfileResponseModel>> getAllUsers() {
        List<UserProfileResponseModel> users = this.userService.getAllUsers().stream()
                .map(userService -> this.modelMapper.map(userService, UserProfileResponseModel.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/profile")
    public ResponseEntity<UserProfileResponseModel> getProfile(HttpSession session) throws Exception {
        String username = session.getAttribute("username").toString();
        UserProfileServiceModel serviceModel = this.userService.getUserByUsername(username);
        UserProfileResponseModel user = this.modelMapper.map(serviceModel, UserProfileResponseModel.class);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
