package com.bezkoder.spring.hibernate.onetomany.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "tutorials")
public class Tutorial {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tutorial_generator")
  private long id;

  @Column(name = "title")
  private String title;

  @Column(name = "version")
  private String version;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "tutorial_id")
    private List<Comment> comments = new ArrayList<>();

  public Tutorial() {

  }

  public Tutorial(String title, String version) {
    this.title = title;
    this.version = version;
   }

  public long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }
/*
  public List<Comment> getComments() {
    return comments;
  }*/

  public void setComments(List<Comment> comments) {
    this.comments = comments;
  }
  
/*
  public Comment getComment() {
    return comment;
  }

  public void setComment(Comment comment) {
    this.comment = comment;
  }*/

    public Comment getComment(){
         return comments.size() > 0 ? comments.get(comments.size() - 1) : null;
    }

/*    public List<Comment> getAllComments(){
         return comments.size() > 0 ? comments : null;
    }*/

  @Override
  public String toString() {
    return "Tutorial [id=" + id + ", title=" + title + ", version=" + version + "]";
  }

}
