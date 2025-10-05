package com.example.corporateapp.Service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.corporateapp.Repository.UserRepo;
import com.example.corporateapp.Model.AppUser;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public Optional<AppUser> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public Optional<AppUser> findById(Long id) {
        return userRepo.findById(id);
    }

    public boolean emailExists(String email) {
        return userRepo.findByEmail(email).isPresent();
    }

    @Transactional
    public AppUser register(String name, String email, String rawPassword) {
        String hash = passwordEncoder.encode(rawPassword);
        AppUser u = AppUser.builder()
                .name(name)
                .email(email)
                .passwordHash(hash)
                .build();
        return userRepo.save(u);
    }

    public Optional<AppUser> authenticate(String email, String rawPassword) {
        return userRepo.findByEmail(email)
                .filter(u -> passwordEncoder.matches(rawPassword, u.getPasswordHash()));
    }
}
