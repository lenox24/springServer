package com.example.demo.model;

import com.example.demo.config.JwtTokenProvider;
import org.json.simple.JSONObject;
import org.springframework.data.annotation.Id;

public class DataModel {
    @Id
    private String id = "";

    private String pass = "";

    private String token = "";

    public void setByJson(JSONObject json) {
        this.id = json.get("id").toString();
        this.pass = json.get("pass").toString();
        this.token = JwtTokenProvider.getUserToken("register", id, pass);
    }

    public String getPass() {
        return pass;
    }
}
