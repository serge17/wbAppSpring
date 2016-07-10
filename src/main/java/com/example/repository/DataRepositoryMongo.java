package com.example.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.model.Data;

public interface DataRepositoryMongo extends MongoRepository<Data, String> {
	public Data findById(String id);
}
