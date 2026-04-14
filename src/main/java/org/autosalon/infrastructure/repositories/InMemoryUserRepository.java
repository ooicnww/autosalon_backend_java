package org.autosalon.infrastructure.repositories;

import org.autosalon.domain.model.users.User;
import org.autosalon.domain.repositories.IUserRepository;

import java.util.*;

public class InMemoryUserRepository extends InMemoryRepository<User> implements IUserRepository {
    @Override
    public void save(User user){
        super.save(user.getId(), user);
    }

    @Override
    public Optional<User> findById(UUID id){
        return super.findById(id);
    }

    @Override
    public List<User> findAll(){
        return super.findAll();
    }

    @Override
    public void delete(UUID id){
        super.delete(id);
    }
}