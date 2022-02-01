package com.bezkoder.spring.hibernate.onetomany.model;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "comments")
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_generator")
  private Long id;

  //@Lob
  private String comment;

  @Column(name = "version")
  private String version;

    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "authorId", nullable = false)
    //@OnDelete(action = OnDeleteAction.CASCADE)
    private Tutorial tutorial;
  
  public Comment() {
  }
  
  public Comment(String comment, String version) {
    this.comment = comment;
    this.version = version;
   }


	
  public Long getId() {
    return id;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

/*
  public Tutorial getTutorial() {
    return tutorial;
  }

  public void setTutorial(Tutorial tutorial) {
    this.tutorial = tutorial;
  }*/

}
