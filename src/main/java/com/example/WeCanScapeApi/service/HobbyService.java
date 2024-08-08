package com.example.WeCanScapeApi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.WeCanScapeApi.DTO.GetHobbyDTO;
import com.example.WeCanScapeApi.repository.HobbyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HobbyService {

    @Autowired
    private HobbyRepository hobbyRepository;

    public List<GetHobbyDTO> findAllHobbies() {
        return hobbyRepository.findAll(Sort.by(Sort.Direction.ASC,
                "label")).stream()
                .map(hobby -> {
                    GetHobbyDTO hobbyDTO = new GetHobbyDTO();
                    hobbyDTO.setLabel(hobby.getLabel());
                    hobbyDTO.setCategoryLabel(hobby.getCategory().getLabel());
                    return hobbyDTO;
                })
                .collect(Collectors.toList());
    }
}
