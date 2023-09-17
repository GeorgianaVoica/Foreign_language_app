package com.example.licenta.classes;

import java.util.HashMap;
import java.util.Map;

public class User implements Comparable<User>{
    private String email;
    private String password;
    private String name;
    private int noPoints;
    private boolean hasPoints;
    private String level;

    public User() {

    }

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.noPoints = 0;
        this.hasPoints = false;
        this.level = "-";

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNoPoints() {
        return noPoints;
    }

    public void setNoPoints(int noPoints) {
        this.noPoints = noPoints;
    }

    public boolean isHasPoints() {
        return hasPoints;
    }

    public void setHasPoints(boolean hasPoints) {
        this.hasPoints = hasPoints;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("email", email);
        result.put("password", password);
        result.put("noPoints", noPoints);
        result.put("hasPoints", hasPoints);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(User o) {
        return o.getNoPoints()-this.getNoPoints();
    }
}
