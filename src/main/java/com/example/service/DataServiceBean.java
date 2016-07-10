package com.example.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Data;
import com.example.repository.DataRepositoryMongo;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class DataServiceBean implements DataService {

	@Autowired
	private DataRepositoryMongo dataRepository;

	@Override
	public Collection<Data> findAll() {
		Collection<Data> data = dataRepository.findAll();
		return data;
	}

	@Override
	@Cacheable(value = "data", key = "#id")
	public Data findOne(String id) {
		Data data = dataRepository.findOne(id);
		return data;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@CachePut(value = "data", key = "#result.id")
	public Data create(Data data) {
		if (data.getId() != null) {
			// Cannot create data with specified id value
			return null;
		}
		data.setId(data.getCountryId() + data.getDate());
		Data savedData = dataRepository.save(data);
		return savedData;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@CachePut(value = "data", key = "#data.id")
	public Data update(Data data) {
		Data dataPersisted = findOne(data.getId());
		if (dataPersisted == null) {
			// Cannot update data that has not been persisted
			return null;
		}
		Data updatedData = dataRepository.save(data);
		return updatedData;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@CacheEvict(value = "data", key = "#id")
	public void delete(String id) {
		dataRepository.delete(id);
	}

	@Override
	@CacheEvict(value="data", allEntries=true)
	public void evictCache() {
		// TODO Auto-generated method stub
		
	}
}
