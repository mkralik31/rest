package com.api.rest.service;

import com.api.rest.model.Tag;

import java.util.List;

public interface TagService {
    Tag saveTag(Tag tag);

    List<Tag> getAllTags();

    List<Tag> getTagsByTutorialId(long tutorialId);

    Tag getTagById(long id);

    boolean existsTagsById(long id);

    void deleteTagById(long id);
}
