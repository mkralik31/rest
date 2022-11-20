package com.api.rest.repository;

import com.api.rest.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByTutorialId(long tutorialId);

    @Transactional
    void deleteByTutorialId(long tutorialId);
}
