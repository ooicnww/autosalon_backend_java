package org.autosalon.application;

import org.autosalon.domain.model.entities.car.Part;
import org.autosalon.domain.repositories.IPartRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PartService {
    private IPartRepository repository;

    public PartService(IPartRepository repository){
        this.repository = repository;
    }

    public Part addPart(Part part){
        repository.save(part);
        return part;
    }

    public List<Part> getAllParts(){
        return repository.findAll();
    }

    public Optional<Part> getPartById(UUID id){
        return repository.findById(id);
    }

    public void updatePart(Part part){
        repository.save(part);
    }

}