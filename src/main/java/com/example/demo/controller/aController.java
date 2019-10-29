package com.example.demo.controller;

import com.example.demo.model.DataModel;
import com.example.demo.repo.DataModelRepo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Map;

@RestController
@RequestMapping(value = "api/a")
public class aController {
    @Autowired
    DataModelRepo dataRepo;

    private String getToken(String token) {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

        return Jwts.parser()
                .setSigningKey("dart")
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody JSONObject reqJson) {
        String token = new DataModel().getUserToken("login", reqJson.get("id").toString(), reqJson.get("pass").toString());

        String subject = getToken(token);

        return new ResponseEntity<>(subject, HttpStatus.OK);
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
