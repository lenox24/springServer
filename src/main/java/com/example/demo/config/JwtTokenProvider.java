package com.example.demo.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {
    private static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String getUserToken(String type) {
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
                .claim("type", type)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key)
                .compact();
    }

    public static String getUserToken(String type, String id, String pass) {
        long expirationTime = 10 * 1000;

        if (type.equals("login")) {
            Map<String, Object> headerMap = new HashMap<>();
            headerMap.put("typ", "JWT");
            headerMap.put("alg", "HS256");

            Map<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("pass", pass);

            return Jwts.builder()
                    .setHeader(headerMap)
                    .setClaims(map)
                    .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                    .signWith(key)
                    .compact();
        } else
            return getUserToken(type);
    }

    public static boolean getExpToken(String token) {
        Map<String, Object> claims =  getTestToken(token);

        Date exp = new Date((long) claims.get(token));
        Date now = new Date((long) claims.get(token));

        return exp.after(now);
    }

    private static Map<String, Object> getTestToken(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }

    public static Map<String, Object> getToken(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }
}
