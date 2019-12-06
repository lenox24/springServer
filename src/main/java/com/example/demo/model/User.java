package com.example.demo.model;

import org.bson.types.ObjectId;
import org.json.simple.JSONObject;
import org.springframework.data.annotation.Id;

public class User {
    @Id
    private ObjectId _id;

    private String id;
    private String password;

    public User() {
        this._id = null;
        this.id = null;
        this.password = null;
    }

    public void setByJson(JSONObject json) {
        this._id = (ObjectId) json.get("_id");
        this.id = json.get("id").toString();
        this.password = json.get("password").toString();
    }

    public String getPassword() {
        return password;
    }
}
