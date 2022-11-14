package com.example.application.data.service;

import com.example.application.data.entity.Misurazione;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MisurazioneService {

    private final MisurazioneRepository repository;

    @Autowired
    public MisurazioneService(MisurazioneRepository repository) {
        this.repository = repository;
    }

    public Optional<Misurazione> get(UUID id) {
        return repository.findById(id);
    }

    public Misurazione update(Misurazione entity) {
        return repository.save(entity);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public Page<Misurazione> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
