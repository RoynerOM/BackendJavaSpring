package com.example.demo.Models;

import javax.persistence.*;
@Entity
@Table(name ="topic")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_Topics;
    @Column
    private String name;

    public Topic() {
    }

    public Topic(String name) {
        this.name = name;
    }

    public long getId_Topics() {
        return id_Topics;
    }

    public void setId_Topics(long id_Topics) {
        this.id_Topics = id_Topics;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
