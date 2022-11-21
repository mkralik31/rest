package com.api.rest.service;

import com.api.rest.model.Tag;
import com.api.rest.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;
    @Override
    public Tag saveTag(Tag tag){
        return tagRepository.save(tag);
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> getTagsByTutorialId(long tutorialId) {
        return tagRepository.findTagsByTutorialsId(tutorialId);
    }

    @Override
    public Tag getTagById(long id) {
        Optional<Tag> tagData = tagRepository.findById(id);

        if(tagData.isPresent()) {
            return tagData.get();
        }

        return null;
    }

    @Override
    public boolean existsTagsById(long id) {
        return tagRepository.existsById(id);
    }

    @Override
    public void deleteTagById(long id) {
        tagRepository.deleteById(id);
    }
}
