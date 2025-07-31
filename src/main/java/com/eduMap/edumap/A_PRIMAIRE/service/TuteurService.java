package com.eduMap.edumap.A_PRIMAIRE.service;

import com.eduMap.edumap.A_PRIMAIRE.Dto.TuteurSimpleDto;
import com.eduMap.edumap.A_PRIMAIRE.repository.TuteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TuteurService {


    @Autowired
    private TuteurRepository tuteurRepository;


    public List<TuteurSimpleDto> getAllTuteurs() {
        return tuteurRepository.findAll().stream()
                .map(t -> new TuteurSimpleDto(t.getId(), t.getNom(), t.getPrenom()))
                .collect(Collectors.toList());
    }

}
