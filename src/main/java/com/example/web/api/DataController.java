package com.example.web.api;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Data;
import com.example.service.DataService;

@RestController
public class DataController {
	
	@Autowired
	private DataService dataService;

	@RequestMapping(value = "/api/data", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Data>> getData() {
		Collection<Data> data = dataService.findAll();
		return new ResponseEntity<Collection<Data>>(data, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/data/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Data> getDataOne(@PathVariable("id") String id) {
		Data data = dataService.findOne(id);
		if (data == null) {
			return new ResponseEntity<Data>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Data>(data, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/data", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Data> createData(@RequestBody Data data) {
		Data savedData = dataService.create(data);
		return new ResponseEntity<Data>(savedData, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/api/data/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Data> updateData(@RequestBody Data data) {
		Data updatedData = dataService.update(data);
		if (updatedData == null) {
			return new ResponseEntity<Data>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Data>(updatedData, HttpStatus.OK);

	}

	@RequestMapping(value = "/api/data/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Data> deleteData(@PathVariable("id") String id, @RequestBody Data data) {
		
		dataService.delete(id);
		return new ResponseEntity<Data>(HttpStatus.NO_CONTENT);
	}

}