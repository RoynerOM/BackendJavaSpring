package com.example.demo.Services;

import com.example.demo.Models.Topic;

import java.util.List;

public interface TopicService {
    Topic save(Topic topic);
    List<Topic> findAll();
    Topic findById(long id)throws Exception;
    Topic update(Topic id);
    void delete(long id)throws Exception;
}
