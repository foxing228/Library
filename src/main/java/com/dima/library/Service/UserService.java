package com.dima.library.Service;

import com.dima.library.Repository.UserRepository;
import com.dima.library.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User add(User user) {
        userRepository.save(user);
        return user;
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User update(User oldUser, User newUser) {
        oldUser.setName(newUser.getName());

        User updatedUser = userRepository.save(oldUser);

        return updatedUser;
    }
}
