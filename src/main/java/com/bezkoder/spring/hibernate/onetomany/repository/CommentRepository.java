package com.bezkoder.spring.hibernate.onetomany.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bezkoder.spring.hibernate.onetomany.model.Comment;
import com.bezkoder.spring.hibernate.onetomany.model.Tutorial;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  List<Comment> findByTutorialId(Long id);
  List<Comment> findByTutorial(Tutorial tutu);
  
/*  @Transactional
  void deleteByTutorialId(long tutorialId);*/
}
