package com.api.rest.service;

import com.api.rest.model.Tutorial;
import com.api.rest.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TutorialServiceImpl implements TutorialService {

    @Autowired
    private TutorialRepository tutorialRepository;

    @Override
    public Tutorial saveTutorial(Tutorial tutorial) {
        Tutorial _tutorial = tutorialRepository.save(tutorial);

        return _tutorial;
    }

    @Override
    public List<Tutorial> getAllTutorials() {
        return tutorialRepository.findAll();
    }
@Override
    public List<Tutorial> getTutorialByTitle(String title) {
        return tutorialRepository.findByTitleContaining(title);
    }

    @Override
    public Tutorial getTutorialById(long id){
        Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

        if(tutorialData.isPresent()) {
            return tutorialData.get();
        }
        return null;
    }

    @Override
    public List<Tutorial> getTutorialByPublished(boolean published){
        return tutorialRepository.findByPublished(published);
    }

    @Override
    public Tutorial updateTutorialById(long id, Tutorial tutorial) {
        Tutorial tutorialData = getTutorialById(id);
        // Optional<Tutorial> tutorialData = tutorialRepository.findById(id);
        if(tutorialData != null) {
          tutorialData.setTitle(tutorial.getTitle());
          tutorialData.setDescription(tutorial.getDescription());
          tutorialData.setPublished(tutorial.isPublished());

          return tutorialData;
        }
        return null;
    }

    @Override
    public void deleteTutorialById(long id) {
        tutorialRepository.deleteTutorialById(id);
    }

    @Override
    public void deleteAllTutorials() {
        tutorialRepository.deleteAll();
    }

    @Override
    public boolean existsTutorialById(long tutorialId) {
        tutorialRepository.existsById(tutorialId);
        return true;
    }
}
