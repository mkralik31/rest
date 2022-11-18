package com.api.rest.controller;

import com.api.rest.model.Tutorial;
import com.api.rest.service.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TutorialController {
    @Autowired
    private TutorialService tutorialService;

    @PostMapping("/tutorials")
    public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
        try {
            Tutorial _tutorial = tutorialService.saveTutorial(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished()));// service method, return

            return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);

        } catch (Exception e) {

            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/tutorials")
    public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title) {
        try {
            List<Tutorial> tutorials = new ArrayList<Tutorial>();

            if (title == null) {
                tutorials = tutorialService.getAllTutorials();
            } else {
                // create a method that will filter by title
                tutorials = tutorialService.getTutorialByTitle(title);
            }
            // get a list of tutorials from database
//            tutorials = tutorialService.getAllTutorials();

            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id) {
        Tutorial tutorial = tutorialService.getTutorialById(id);
        // method to return tutorial by id from database

        if (tutorial != null) {
            return new ResponseEntity<>(tutorial, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/tutorials/published")
    public ResponseEntity<List<Tutorial>> getTutorialsByPublished() {
        try{
            List<Tutorial> tutorials = tutorialService.getTutorialByPublished(true);

            if(tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(tutorials, HttpStatus.OK);
            }
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
        Tutorial _tutorial = tutorialService.updateTutorialById(id, tutorial);

        if(_tutorial != null) {
            return new ResponseEntity<>(tutorialService.saveTutorial(_tutorial), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
        try {
            tutorialService.deleteTutorialById(id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/tutorials")
    public ResponseEntity<HttpStatus> deleteAllTutorials() {
        try {
            tutorialService.deleteAllTutorials();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>((HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }
}