package com.example.demo.Services;

import com.example.demo.Models.Topic;
import com.example.demo.Repositories.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.List;

    @Service
    public class TopicServiceImp implements TopicService {
        private final TopicRepository topicRepository;

        public TopicServiceImp(TopicRepository topicRepository) { this.topicRepository = topicRepository; }

        @Override
        public Topic save(Topic topic) {
            return topicRepository.save(topic);
        }

        @Override
        public List<Topic> findAll() { return topicRepository.findAll(); }

        @Override
        public Topic findById(long id) throws Exception { return topicRepository.findById(id).orElseThrow(Exception::new); }

        @Override
        public Topic update(Topic topic) { return topicRepository.save(topic); }

        @Override
        public void delete(long id) throws Exception { topicRepository.deleteById(id); }
}
