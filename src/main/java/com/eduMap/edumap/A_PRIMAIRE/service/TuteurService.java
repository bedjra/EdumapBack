package skool.saas.skool.A_PRIMAIRE.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skool.saas.skool.A_PRIMAIRE.Dto.TuteurSimpleDto;
import skool.saas.skool.A_PRIMAIRE.repository.TuteurRepository;

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
