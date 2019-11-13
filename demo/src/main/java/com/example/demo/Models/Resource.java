package com.example.demo.Models;

import javax.persistence.*;

@Entity
@Table(name ="resources")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String description;
    @Column
    private String url;
    @ManyToOne
    @JoinColumn(name = "id_Topics")
    private Topic topic;
    public Resource() {
    }

    public Resource(Topic topic, String description, String url) {
        this.topic = topic;
        this.description = description;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
