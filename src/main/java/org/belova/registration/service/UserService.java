package org.belova.registration.service;

import org.belova.registration.models.User;

import java.util.List;

public interface UserService {
    User addUser(User user);
    void deleteUser(User user);
    User getByEmail(String email);
    List<User> getUsers();
}
