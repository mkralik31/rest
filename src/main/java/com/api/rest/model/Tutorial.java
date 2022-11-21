package com.api.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tutorials")
//@JsonIgnoreProperties
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Tutorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "published")
    private boolean published;

//    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "tutorials_tags",
            joinColumns = {@JoinColumn(name = "tutorial_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private List<Tag> tags = new ArrayList<Tag>();

    public Tutorial() {
    }

    public Tutorial(String title, String description, boolean isPublished) {
        this.title = title;
        this.description = description;
        this.published = isPublished;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
        tag.getTutorials().add(this);
    }

    public void removeTag(Tag tag) {
        this.tags.remove(tag);
        tag.getTutorials().remove(this);
    }

    @Override
    public String toString() {
        return "Tutorial{" + "id=" + id + ", title='" + title + '\'' + ", description='" + description + '\'' + ", published=" + published + ", tags=" + tags + '}';
    }
}
