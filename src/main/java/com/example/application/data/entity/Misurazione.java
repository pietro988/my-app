package com.example.application.data.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Misurazione {

    @Id
    private Integer id;
    private Integer consumo;

    private Date data;

    public Misurazione(int id, int consumo, Date data) {
        this.id = id;
        this.consumo = consumo;
        this.data = data;
    }

    public Misurazione() {

    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getConsumo() {
        return consumo;
    }
    public void setConsumo(Integer consumo) {
        this.consumo = consumo;
    }

    public Date getDate() {
        return data;
    }
    public void setDate(Date data) {
        this.data = data;
    }

}
