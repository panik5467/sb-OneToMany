package com.bezkoder.spring.hibernate.onetomany.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.hibernate.onetomany.exception.ResourceNotFoundException;
import com.bezkoder.spring.hibernate.onetomany.model.Tutorial;
import com.bezkoder.spring.hibernate.onetomany.model.Comment;
import com.bezkoder.spring.hibernate.onetomany.repository.TutorialRepository;
import com.bezkoder.spring.hibernate.onetomany.repository.CommentRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class Api {

  @Autowired
  TutorialRepository tutorialRepo;
  
  @Autowired
  CommentRepository commentRepo;

  @GetMapping("/post") // /post?title=toto
  public void addTuto(@RequestParam(required = false) String title) {
	Tutorial post = new Tutorial ("Proposal " + title,"AD");
 
 List<Comment> list = new ArrayList<> ();
 list.add(new Comment("My 1 " + title,"AA"));
 list.add(new Comment("My 2 " + title,"AC"));
 list.add(new Comment("My 3 " + title,"AD"));
	post.setComments(list);

	tutorialRepo.save(post);
}

  @GetMapping("/props/{id}/revs")
  public ResponseEntity<List<Comment>> getCommentByTutorialId(@PathVariable("id") long id) {
    Tutorial tuto = tutorialRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));
	
	//List<Comment> list = commentRepo.findByTutorialId(id);
	List<Comment> list = commentRepo.findByTutorial(tuto);
	

    return new ResponseEntity<>(list, HttpStatus.OK);
  }


  @GetMapping("/props")
  public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title) {
    List<Tutorial> tutorials = new ArrayList<Tutorial>();

    if (title == null)
      tutorialRepo.findAll().forEach(tutorials::add);
    else
      tutorialRepo.findByTitleContaining(title).forEach(tutorials::add);

    if (tutorials.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(tutorials, HttpStatus.OK);
  }

  @GetMapping("/props/{id}")
  public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id) {
    Tutorial tutorial = tutorialRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));

    return new ResponseEntity<>(tutorial, HttpStatus.OK);
  }


  @PutMapping("/tutorials/{id}")
  public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
    Tutorial _tutorial = tutorialRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));

    _tutorial.setTitle(tutorial.getTitle());
    _tutorial.setVersion(tutorial.getVersion());
   // _tutorial.setPublished(tutorial.isPublished());
    
    return new ResponseEntity<>(tutorialRepo.save(_tutorial), HttpStatus.OK);
  }

  @DeleteMapping("/tutorials/{id}")
  public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
    tutorialRepo.deleteById(id);
    
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("/tutorials")
  public ResponseEntity<HttpStatus> deleteAllTutorials() {
    tutorialRepo.deleteAll();
    
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/tutorials/version/{version}")
  public ResponseEntity<List<Tutorial>> findByVersion(@PathVariable("version") String version) {
    List<Tutorial> tutorials = tutorialRepo.findByVersion(version);

    if (tutorials.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    return new ResponseEntity<>(tutorials, HttpStatus.OK);
  }
}
