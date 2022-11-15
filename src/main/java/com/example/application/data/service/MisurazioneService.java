package com.example.application.data.service;

import com.example.application.data.entity.Misurazione;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class MisurazioneService {


    @Autowired
    private JdbcTemplate template;


    public List<Misurazione> findAll(){
        try {

            return template.query("SELECT * FROM misurazioni",
                    (rs, rowNum) -> new Misurazione(rs.getInt("id"),rs.getInt("consumo"), rs.getTimestamp("data")));

        } catch (Exception e){
            return new ArrayList<Misurazione>();
        }
    }

}
