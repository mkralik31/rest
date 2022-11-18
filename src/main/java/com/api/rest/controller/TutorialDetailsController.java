package com.api.rest.controller;

import com.api.rest.model.Tutorial;
import com.api.rest.model.TutorialDetails;
import com.api.rest.service.TutorialDetailService;
import com.api.rest.service.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api")
public class TutorialDetailsController {

    @Autowired
    private TutorialDetailService tutorialDetailService;

    @Autowired
    private TutorialService tutorialService;

//    @Autowired
//    public TutorialDetailsController(TutorialDetailService tutorialDetailService, TutorialService tutorialService) {
//        this.tutorialDetailService = tutorialDetailService;
//        this.tutorialService = tutorialService;
//    }

    @PostMapping("/tutorials/{tutorialId}/details")
    public ResponseEntity<TutorialDetails> createDetails(@PathVariable(value = "tutorialId") /*long*/ Long tutorialId, @RequestBody TutorialDetails detailsRequest) {
        Tutorial tutorial = tutorialService.getTutorialById(tutorialId);
        if (tutorial == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            detailsRequest.setCreatedOn(new Date());
            detailsRequest.setTutorial(tutorial);

            // save tutorial detail in database
            TutorialDetails details = tutorialDetailService.saveTutorialDetails(detailsRequest);

            return new ResponseEntity<>(details, HttpStatus.CREATED);
        }

    }
}
