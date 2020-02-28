package org.belova.registration.service;

import org.belova.registration.models.User;
import org.belova.registration.repositories.UserRepository;
import org.belova.registration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    private final BCryptPasswordEncoder encoding = new BCryptPasswordEncoder(16);

    @Override
    public User addUser(User user) {
        user.setPassword(encoding.encode(user.getPassword()));
        return userRepository.save(user);
    }
    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }
    @Override
    public User getByEmail(String email) {
            List<User> users = this.userRepository.findUserByEmail(email);
            if(users.isEmpty()) {
                return null;
            }
            return users.get(0);
    }
    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
