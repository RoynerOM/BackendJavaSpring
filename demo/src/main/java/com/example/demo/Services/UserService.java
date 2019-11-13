package com.example.demo.Services;

import com.example.demo.Models.User;

import java.util.List;

public interface UserService  {
    User save(User user);
    List<User> findAll();
    User findById(long id) throws Exception;
    User update(User user);
    void delete(long id) throws Exception;
    User findByEmailAndId(String email, String password);

}
