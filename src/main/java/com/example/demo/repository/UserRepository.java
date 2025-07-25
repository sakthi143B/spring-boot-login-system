package com.example.demo.repository;
import java.util.List;

import com.example.demo.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    List<User> findAllByEmailAndPassword(String email, String password);
}
