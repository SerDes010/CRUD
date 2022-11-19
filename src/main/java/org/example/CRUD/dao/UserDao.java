package org.example.CRUD.dao;

import org.example.CRUD.model.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUsers();
    void createUser(User user);
    void deleteUser(int id);
    void updateUser(int id, User user);
    User showUser(int id);
}
