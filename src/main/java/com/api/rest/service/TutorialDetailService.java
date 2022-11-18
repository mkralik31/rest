package com.api.rest.service;

import com.api.rest.model.TutorialDetails;

public interface TutorialDetailService {
    TutorialDetails saveTutorialDetails(TutorialDetails details);

    TutorialDetails getDetailsById(long id);

    void deleteDetailsById(long id);
}
