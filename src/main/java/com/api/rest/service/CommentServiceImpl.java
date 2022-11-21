package com.api.rest.service;

import com.api.rest.model.Comment;
import com.api.rest.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment getCommentById(long id) {
        Optional<Comment> commentData = commentRepository.findById(id);

        if(commentData.isPresent()) {
            return commentData.get();
        }

        return null;
    }

    @Override
    public List<Comment> getCommentsByTutorialId(long tutorialId) {
        return commentRepository.findByTutorialId(tutorialId);
    }

    @Override
    public void deleteCommentById(long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public void deleteCommentsByTutorialId(long tutorialId) {
        commentRepository.deleteByTutorialId(tutorialId);
    }
}
