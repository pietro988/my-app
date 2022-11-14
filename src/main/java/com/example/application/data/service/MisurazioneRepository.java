package com.example.application.data.service;

import com.example.application.data.entity.Misurazione;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MisurazioneRepository extends JpaRepository<Misurazione, UUID> {

}