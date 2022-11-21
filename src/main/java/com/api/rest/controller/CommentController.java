package com.api.rest.controller;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.api.rest.model.Comment;
import com.api.rest.model.Tutorial;
import com.api.rest.service.CommentService;
import com.api.rest.service.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/comments/id")
    public ResponseEntity<Comment> getCommentById(@PathVariable(value = "id") long id) {
        // get comment by id
        Comment comment = commentService.getCommentById(id);

        if(comment != null) {
            return new ResponseEntity<>(comment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/tutorials/{tutorialId}/comments")
    public ResponseEntity<List<Comment>> getAllCommentsByTutorialId(@PathVariable(value = "tutorialId") long tutorialId) {
        if(!tutorialService.existsTutorialById(tutorialId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Comment> comment = commentService.getCommentsByTutorialId(tutorialId);

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable(value = "id") long id, @RequestBody Comment commentRequest) {
        Comment comment = commentService.getCommentById(id);

        if(comment == null) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } else {

            comment.setContent(commentRequest.getContent());

            return new ResponseEntity<>(commentService.saveComment(comment), HttpStatus.OK);
        }
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("id") long id) {
        try {
            commentService.deleteCommentById(id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/tutorial/{tutorialId}/comments")
    public ResponseEntity<HttpStatus> deleteAllCommentsOfTutorial(@PathVariable("tutorialId") long tutorialId) {
        try {
            if (!tutorialService.existsTutorialById(tutorialId)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            } else {
                commentService.deleteCommentsByTutorialId(tutorialId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
