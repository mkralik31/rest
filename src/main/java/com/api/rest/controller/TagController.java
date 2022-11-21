package com.api.rest.controller;

import com.api.rest.model.Tag;
import com.api.rest.model.Tutorial;
import com.api.rest.service.TagService;
import com.api.rest.service.TagServiceImpl;
import com.api.rest.service.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TagController {

    private TutorialService tutorialService;

    private TagService tagService;

    @Autowired
    public TagController(TutorialService tutorialService, TagService tagService) {
        this.tutorialService = tutorialService;
        this.tagService = tagService;
    }

    @PostMapping("tutorials/{tutorialId}/tags")
    public ResponseEntity<Tag> addTag(@PathVariable(value = "tutorialId") long tutorialId, @RequestBody Tag tagRequest) {
        try {
            // create a new tag
            Tag tag = tagService.saveTag(new Tag(tagRequest.getName()));

            // check if tag was inserted
            if(tag == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                // check if tutorial exists
                Tutorial tutorial = tutorialService.getTutorialById(tutorialId);
                if(tutorial == null) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                } else {
                    // associate a tag to a tutorial
                    tutorial.addTag(tag);

                    tutorialService.saveTutorial(tutorial);

                    return new ResponseEntity<>(tag, HttpStatus.CREATED);
                }
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
    * ASSOCIATE AN EXISTING TAG TO AN EXISTING TUTORIAL
    * */

    @GetMapping("/tags")
    public ResponseEntity<List<Tag>> getAllTags() {
        try {
            List<Tag> tags = tagService.getAllTags();

            if(tags.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tags, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tutorials/{tutorialId}/tags")
    public ResponseEntity<List<Tag>> getAllTagsByTutorialId(@PathVariable(value = "tutorialId") long tutorialId) {
        try {
            if(!tutorialService.existsTutorialById(tutorialId)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                List<Tag> tags = tagService.getTagsByTutorialId(tutorialId);

                if(tags.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity<>(tags, HttpStatus.OK);
                }
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("tags/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable(value = "id") long id) {
        try {

            Tag tag = tagService.getTagById(id);

            if(tag == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(tag, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tags/{tagId}/tutorials")
    public ResponseEntity<List<Tutorial>> getAllTutorialsByTagId(@PathVariable(value = "tagId") long tagId) {
        try {
            // check if tag exists
            if(!tagService.existsTagsById(tagId)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // get tutorials
            List<Tutorial> tutorials = tutorialService.getTutorialsByTagsId(tagId);

            // check if got tutorials info
            if(tutorials.isEmpty()) {
                return new ResponseEntity<>(tutorials, HttpStatus.OK);
            } else {
                // return those tutorials
                return new ResponseEntity<>(tutorials, HttpStatus.OK);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/tags/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable("id") long id, @RequestBody Tag tagRequest) {
        try {
            // get a tag by id
            Tag tag = tagService.getTagById(id);

            // check if tag has info
            if(tag == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                // update the tag instance
                tag.setName(tagRequest.getName());

                // save tag to database
                return new ResponseEntity<>(tagService.saveTag(tag), HttpStatus.OK);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/tags/{id}")
    public ResponseEntity<HttpStatus> deleteTag(@PathVariable(value = "id") long id) {
        try {
            // from tag id, get all tutorials associated
            Tag tag = tagService.getTagById(id);

            if (tag == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                List<Tutorial> tutorials = tutorialService.getTutorialsByTagsId(id);

                // delete tag by id
                if (!tutorials.isEmpty()) {
                    for (Tutorial tutorial : tutorials) { // delete that associations
                        tutorial.removeTag(tag);
                    }
                }
                tagService.deleteTagById(id); // for cases where we have tags with no tutorials associated
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

//                if(tutorials.isEmpty()) {
//                    tagService.deleteTagById(id); // for cases where we have tags with no tutorials associated
//                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//                } else {
//                    for (Tutorial tutorial : tutorials) {
//                        tutorial.removeTag(tag);
//                    }
//                    tagService.deleteTagById(id);
//                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//                }

            }
            } catch(Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @DeleteMapping("/tutorials/{tutorialId}/tags/{tagId}")
//    public ResponseEntity<HttpStatus> deleteTagFromTutorial(@PathVariable("tutorialId") long tutorialId, @PathVariable("tagId") long tagId) {
//        try {
//            // get tutorial by id
//            Tutorial tutorial = tutorialService.getTutorialById(tutorialId);
//
//            // check if tutorial is null
//            if (tutorial == null) {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            } else {
//
//                Tag tag = tagService.getTagById(tagId);
//
//                if (tag == null) {
//                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//                } else {
//                    // remove association between tag and tutorial
//                    tutorial.removeTag(tag);
//
//                    // save tutorial
//                    tutorialService.saveTutorial(tutorial);
//                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//                }
//            }
//        } catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
