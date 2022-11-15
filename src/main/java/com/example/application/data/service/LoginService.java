package com.example.application.data.service;


import com.example.application.data.entity.User;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class LoginService {

    @Autowired
    private JdbcTemplate template;

    public class AuthException extends Exception {

    }

    public void authenticate(String username,String password) throws AuthException
    {
        // QUERY PER VERIFICARE CHE ESISTA UN UTENTE CON QUELL'USERNAME E PASSWORD

        List<User> user_tmp = template.query("SELECT * FROM utenti WHERE username = '"+username+"'",
                (rs,rowNum)-> new User(rs.getString("username"),rs.getString("password"),rs.getString("ruolo")));

        // VERIFICA

        if(!user_tmp.isEmpty() && Objects.equals(user_tmp.get(0).getUsername(), username) && user_tmp.get(0).checkPassword(password)){
            VaadinSession.getCurrent().setAttribute(User.class, user_tmp.get(0));

            // .... inserire metodi per far visualizzare pagine in base al privilegio (ruolo)
        }

        else {
            throw new AuthException();
        }


    }



}
