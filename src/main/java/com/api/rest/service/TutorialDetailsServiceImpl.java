package com.api.rest.service;

import com.api.rest.model.TutorialDetails;
import com.api.rest.repository.TutorialDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TutorialDetailsServiceImpl implements TutorialDetailService {
    @Autowired
    private TutorialDetailsRepository detailsRepository;

    @Override
    public TutorialDetails saveTutorialDetails(TutorialDetails details) {
        return detailsRepository.save(details);
    }

    @Override
    public TutorialDetails getDetailsById(long id) {
        Optional<TutorialDetails> detailsData = detailsRepository.findById(id);

        if(detailsData.isPresent()) {
            return detailsData.get();
        }
        return null;
    }

    @Override
    public void deleteDetailsById(long id) {
        detailsRepository.deleteById(id);
    }
}
