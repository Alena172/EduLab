package com.example.course2.services;

import com.example.course2.models.Role;
import com.example.course2.models.User;
import com.example.course2.repositories.UserRepository;
import com.example.course2.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing users.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Find a user by email.
     *
     * @param email the email of the user
     * @return an optional containing the user if found, or empty if not found
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Add a new user.
     *
     * @param user the user to add
     */
    public void addUser(User user) {
        if (user.getRole() == null) {
            user.setRole(Role.Student);
        }

        if (!userRepository.existsByEmail(user.getEmail())) {
            user.setCreatedAt(LocalDateTime.now());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }
    }

    /**
     * Save an existing user.
     *
     * @param user the user to save
     */
    public void save(User user) {
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        existingUser.setName(user.getName());
        existingUser.setSurname(user.getSurname());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());
        existingUser.setRole(user.getRole());
        userRepository.save(existingUser);
    }

    /**
     * Find all teachers.
     *
     * @return a list of teachers
     */
    public List<User> findTeachers() {
        return userRepository.findByRole(Role.Teacher);
    }

    /**
     * Find all users.
     *
     * @return a list of all users
     */
    public List<User> findAll() {
        Iterable<User> allUsers = userRepository.findAll();
        List<User> users = new ArrayList<>();
        for (User user : allUsers) {
            users.add(user);
        }
        return users;
    }

    /**
     * Find a user by ID.
     *
     * @param id the ID of the user
     * @return the user
     */
    public User findUserById(Long id) {
        return userRepository.findUserById(id);
    }

    /**
     * Change the password of a user.
     *
     * @param user the user
     * @param newPassword the new password
     */
    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    /**
     * Delete a user by ID.
     *
     * @param id the ID of the user
     */
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() == Role.Teacher) {
            courseRepository.updateInstructorToNull(user);
        }
        userRepository.deleteById(id);
    }
}
