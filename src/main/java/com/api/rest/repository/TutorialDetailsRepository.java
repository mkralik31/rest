package com.api.rest.repository;

import com.api.rest.model.TutorialDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface TutorialDetailsRepository extends JpaRepository<TutorialDetails, Long> {

    @Transactional
    void deleteById(long id);

    @Transactional
    void deleteByTutorialId(long tutorialId);

}
