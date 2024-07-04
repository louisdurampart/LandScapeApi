package com.example.WeCanScapeApi.controler;

import com.example.WeCanScapeApi.modele.UserHobby;
import com.example.WeCanScapeApi.repository.UserHobbyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-hobbies")
public class UserHobbyController {

    @Autowired
    private UserHobbyRepository userHobbyRepository;

    @GetMapping
    public List<UserHobby> getAllUserHobbies() {
        return userHobbyRepository.findAll();
    }

    @GetMapping("/{id}")
    public UserHobby getUserHobbyById(@PathVariable Integer id) {
        return userHobbyRepository.findById(id).orElse(null);
    }

    @PostMapping
    public UserHobby createUserHobby(@RequestBody UserHobby userHobby) {
        return userHobbyRepository.save(userHobby);
    }

    @PutMapping("/{id}")
    public UserHobby updateUserHobby(@PathVariable Integer id, @RequestBody UserHobby userHobby) {
        userHobby.setId(id);
        return userHobbyRepository.save(userHobby);
    }

    @DeleteMapping("/{id}")
    public void deleteUserHobby(@PathVariable Integer id) {
        userHobbyRepository.deleteById(id);
    }
}
