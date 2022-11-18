package com.api.rest.service;

import com.api.rest.model.TutorialDetails;
import com.api.rest.repository.TutorialDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TutorialDetailsServiceImpl implements TutorialDetailService {
    @Autowired
    private TutorialDetailsRepository detailsRepository;

    @Override
    public TutorialDetails saveTutorialDetails(TutorialDetails details) {
        return detailsRepository.save(details);
    }
}
