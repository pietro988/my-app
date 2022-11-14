package com.example.application.data.entity;

import javax.persistence.Entity;

@Entity
public class Misurazione extends AbstractEntity {

    private Integer idSensore;
    private Integer potenzaConsumata;

    public Integer getIdSensore() {
        return idSensore;
    }
    public void setIdSensore(Integer idSensore) {
        this.idSensore = idSensore;
    }
    public Integer getPotenzaConsumata() {
        return potenzaConsumata;
    }
    public void setPotenzaConsumata(Integer potenzaConsumata) {
        this.potenzaConsumata = potenzaConsumata;
    }

}
