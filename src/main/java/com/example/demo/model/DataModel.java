package com.example.demo.model;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.json.simple.JSONObject;
import org.springframework.data.annotation.Id;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class DataModel {
    @Id
    private String id = "";

    private String pass = "";

    public void setByJson(JSONObject json) {
        this.id = json.get("id").toString();
        this.pass = json.get("pass").toString();
    }

    public String getPass() {
        return pass;
    }

    public String getUserToken(String type) {
        long expirationTime;
        switch (type) {
            case "access":
                expirationTime = 30 * 60 * 1000;
                break;
            case "refresh":
                expirationTime = 7 * 24 * 60 * 60 * 1000;
                break;
            default:
                return null;
        }

        return Jwts.builder()
                .setSubject(this.id)
                .claim("type", type)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256))
                .compact();
    }

    public String getUserToken(String type, String id, String pass) {
        long expirationTime = 10 * 1000;

        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        return Jwts.builder()
                .setSubject(this.id)
                .claim("type", type)
                .claim("id", id)
                .claim("pass", pass)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key)
                .compact();

    }
}
