package com.example.service;

import java.util.Collection;

import com.example.model.Data;

public interface DataService {
	
	Collection<Data> findAll();
	Data findOne(String id);
	Data create(Data data);
	Data update(Data data);
	void delete(String id);
	void evictCache();
}
