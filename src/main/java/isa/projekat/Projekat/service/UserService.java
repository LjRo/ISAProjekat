package isa.projekat.Projekat.service;

import isa.projekat.Projekat.model.User;

import java.util.List;

public interface UserService {
    User findById(Long id);
    User findByUsername(String username);
    List<User> findAll();
}
