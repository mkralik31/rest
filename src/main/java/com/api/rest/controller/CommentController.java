package com.api.rest.controller;

import com.api.rest.model.Comment;
import com.api.rest.model.Tutorial;
import com.api.rest.service.CommentService;
import com.api.rest.service.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private TutorialService tutorialService;

    @PostMapping("/tutorial/{tutorialId}/comments")
    public ResponseEntity<Comment> createComment(@PathVariable(value = "tutorialId") long tutorialId, @RequestBody Comment commentRequest) {
        Tutorial tutorial = tutorialService.getTutorialById(tutorialId);

        if(tutorial == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        commentRequest.setTutorial(tutorial);

        Comment comment = commentService.saveComment(commentRequest);

        return new ResponseEntity<>(comment, HttpStatus.CREATED);

    }


}
