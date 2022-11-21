package com.api.rest.service;

import com.api.rest.model.Comment;

import java.util.List;

public interface CommentService {
    Comment saveComment(Comment comment);

    Comment getCommentById(long id);

    List<Comment> getCommentsByTutorialId(long tutorialId);

    void deleteCommentById(long id);

    void deleteCommentsByTutorialId(long tutorialId);
}
