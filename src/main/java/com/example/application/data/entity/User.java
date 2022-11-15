package com.example.application.data.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class User {

    @Id
    private String username;

    private String password;

    private String ruolo;


    public User(){

    }

    public User(String username,String password, String ruolo){
        this.username = username;
        this.password = password;
        this.ruolo = ruolo;
    }


    public boolean checkPassword(String password){
        return Objects.equals(password, this.password);
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }
}
