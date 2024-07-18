package com.example.WeCanScapeApi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.WeCanScapeApi.modele.Hobby;
import com.example.WeCanScapeApi.modele.User;
import com.example.WeCanScapeApi.modele.UserHobby;
import com.example.WeCanScapeApi.repository.HobbyRepository;
import com.example.WeCanScapeApi.repository.UserHobbyRepository;
import com.example.WeCanScapeApi.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserHobbyRepository userHobbyRepository;

    @Autowired
    private HobbyRepository hobbyRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Hobby> getHobbiesByUserId(Integer userId) {
        List<UserHobby> userHobbies = userHobbyRepository.findByUserId(userId);
        return userHobbies.stream().map(UserHobby::getHobby).collect(Collectors.toList());
    }

    public Optional<UserHobby> addHobbyToUser(Integer userId, Integer hobbyId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Hobby> hobby = hobbyRepository.findById(hobbyId);

        if (user.isPresent() && hobby.isPresent()) {
            UserHobby userHobby = new UserHobby();
            userHobby.setUser(user.get());
            userHobby.setHobby(hobby.get());
            return Optional.of(userHobbyRepository.save(userHobby));
        }
        return Optional.empty();
    }

    public boolean deleteUserHobby(Integer userId, Integer hobbyId) {
        Optional<UserHobby> userHobby = userHobbyRepository.findByUserIdAndHobbyId(userId, hobbyId);
        
        if (userHobby.isPresent()) {
            userHobbyRepository.delete(userHobby.get());
            return true;
        }
        return false;
    }
}
