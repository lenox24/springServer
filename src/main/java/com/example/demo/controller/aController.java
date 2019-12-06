package com.example.demo.controller;

import com.example.demo.util.JwtTokenProvider;
import com.example.demo.model.User;
import com.example.demo.repo.UserRepo;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "api/a")
public class aController {
    private final
    UserRepo userRepo;

    public aController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody User reqJson) {
        User user = userRepo.findByUserId(reqJson.getId());

        if (null == user) {
            return new ResponseEntity<>("id doesn't exits", HttpStatus.BAD_REQUEST);
        } else if (!user.getPassword().equals(reqJson.getPassword())) {
            return new ResponseEntity<>("Password is incorrect", HttpStatus.BAD_REQUEST);
        }

        JSONObject resJson = new JSONObject();
        resJson.put("accessToken", JwtTokenProvider.getUserToken("access"));
        resJson.put("refreshToken", JwtTokenProvider.getUserToken("refresh"));

        return new ResponseEntity<>(resJson, HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity register(@RequestBody JSONObject reqJson) {
        if (userRepo.findById(reqJson.get("id").toString()).isPresent()) {
            return new ResponseEntity<>("already exist", HttpStatus.BAD_REQUEST);
        }
        User model = new User();
        model.setByJson(reqJson);
        userRepo.insert(model);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
