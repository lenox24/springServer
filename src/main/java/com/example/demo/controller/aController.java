package com.example.demo.controller;

import com.example.demo.config.JwtTokenProvider;
import com.example.demo.model.DataModel;
import com.example.demo.repo.DataModelRepo;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "api/a")
public class aController {
    private final
    DataModelRepo dataRepo;

    public aController(DataModelRepo dataRepo) {
        this.dataRepo = dataRepo;
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody JSONObject reqJson) {
        String token = JwtTokenProvider.getUserToken("login", reqJson.get("id").toString(), reqJson.get("pass").toString());

        Map<String, Object> claims = JwtTokenProvider.getToken(token);

        String id= claims.get("id").toString();

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity register(@RequestBody JSONObject reqJson) {
        if (dataRepo.findById(reqJson.get("id").toString()).isPresent()) {
            return new ResponseEntity<>("already exist", HttpStatus.BAD_REQUEST);
        }
        DataModel model = new DataModel();
        model.setByJson(reqJson);
        dataRepo.insert(model);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
