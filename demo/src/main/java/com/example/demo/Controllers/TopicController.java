package com.example.demo.Controllers;

import com.example.demo.Models.Topic;
import com.example.demo.Services.TopicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
            this.topicService = topicService;
        }
        @GetMapping("/topic")
        public ResponseEntity<List<Topic>> findAll(){ return ResponseEntity.ok().body(topicService.findAll()); }

        @PostMapping("/topic")
        public ResponseEntity<Topic> save(@RequestBody Topic topic){ return ResponseEntity.ok().body(topicService.save(topic)); }

        @GetMapping("/topic/{id}")
        public ResponseEntity<Topic> findById(@PathVariable long id)throws Exception{ return ResponseEntity.ok().body(topicService.findById(id)); }

        @PutMapping("/topic")
        public ResponseEntity<Topic> update(@RequestBody Topic topic){ return ResponseEntity.ok().body(topicService.update(topic)); }

        @DeleteMapping("/topic/{id}")
        public ResponseEntity<Topic> delete(@PathVariable long id)throws Exception{ topicService.delete(id);return ResponseEntity.ok().build(); }
    }


