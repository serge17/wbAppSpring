package com.example.web.api;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.example.AbstractControllerTest;
import com.example.model.Data;
import com.example.service.DataService;

@Transactional
public class DataControllerMocksTest extends AbstractControllerTest {

	@Mock
	private DataService dataService;
	@InjectMocks
	private DataController dataController;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		setUp(dataController);

	}

	@Test
	public void testGetData() throws Exception {

		// Create some test data
		Collection<Data> list = getEntityListStubData();

		// Stub the GreetingService.findAll method return value
		when(dataService.findAll()).thenReturn(list);

		// Perform the behavior being tested
		String uri = "/api/data";

		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();

		// Extract the response status and body
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// Verify the GreetingService.findAll method was invoked once
		verify(dataService, times(1)).findAll();

		// Perform standard JUnit assertions on the response
		Assert.assertEquals("failure - expected HTTP status 200", 200, status);
		Assert.assertTrue("failure - expected HTTP response body to have a value", content.trim().length() > 0);

	}

	@Test
	public void testGetDataOne() throws Exception {

		// Create some test data
		String id = "ZW1961";
		Data entity = getEntityStubData();

		// Stub the GreetingService.findOne method return value
		when(dataService.findOne(id)).thenReturn(entity);

		// Perform the behavior being tested
		String uri = "/api/data/{id}";

		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.APPLICATION_JSON))
				.andReturn();

		// Extract the response status and body
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// Verify the GreetingService.findOne method was invoked once
		verify(dataService, times(1)).findOne(id);

		// Perform standard JUnit assertions on the test results
		Assert.assertEquals("failure - expected HTTP status 200", 200, status);
		Assert.assertTrue("failure - expected HTTP response body to have a value", content.trim().length() > 0);
	}

	@Test
	public void testGetDataNotFound() throws Exception {

		// Create some test data
		String id = "US1000";

		// Stub the GreetingService.findOne method return value
		when(dataService.findOne(id)).thenReturn(null);

		// Perform the behavior being tested
		String uri = "/api/data/{id}";

		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.APPLICATION_JSON))
				.andReturn();

		// Extract the response status and body
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// Verify the GreetingService.findOne method was invoked once
		verify(dataService, times(1)).findOne(id);

		// Perform standard JUnit assertions on the test results
		Assert.assertEquals("failure - expected HTTP status 404", 404, status);
		Assert.assertTrue("failure - expected HTTP response body to be empty", content.trim().length() == 0);

	}

	@Test
	public void testCreateData() throws Exception {

		// Create some test data
		Data entity = getEntityStubData();

		// Stub the GreetingService.create method return value
		when(dataService.create(any(Data.class))).thenReturn(entity);

		// Perform the behavior being tested
		String uri = "/api/data";
		String inputJson = super.mapToJson(entity);

		MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();

		// Extract the response status and body
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// Verify the GreetingService.create method was invoked once
		verify(dataService, times(1)).create(any(Data.class));

		// Perform standard JUnit assertions on the test results
		Assert.assertEquals("failure - expected HTTP status 201", 201, status);
		Assert.assertTrue("failure - expected HTTP response body to have a value", content.trim().length() > 0);

		Data createdEntity = super.mapFromJson(content, Data.class);

		Assert.assertNotNull("failure - expected entity not null", createdEntity);
		Assert.assertNotNull("failure - expected id attribute not null", createdEntity.getId());
		Assert.assertEquals("failure - expected text attribute match", entity.getCountryId(),
				createdEntity.getCountryId());
	}

	@Test
	public void testUpdateData() throws Exception {

		// Create some test data
		Data entity = getEntityStubData();
		entity.setCountryValue(entity.getCountryValue() + " test");
		String id = "US1960";

		// Stub the DataService.update method return value
		when(dataService.update(any(Data.class))).thenReturn(entity);

		// Perform the behavior being tested
		String uri = "/api/data/{id}";
		String inputJson = super.mapToJson(entity);

		MvcResult result = mvc.perform(MockMvcRequestBuilders.put(uri, id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();

		// Extract the response status and body
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// Verify the DataService.update method was invoked once
		verify(dataService, times(1)).update(any(Data.class));

		// Perform standard JUnit assertions on the test results
		Assert.assertEquals("failure - expected HTTP status 200", 200, status);
		Assert.assertTrue("failure - expected HTTP response body to have a value", content.trim().length() > 0);

		Data updatedEntity = super.mapFromJson(content, Data.class);

		Assert.assertNotNull("failure - expected entity not null", updatedEntity);
		Assert.assertEquals("failure - expected id attribute unchanged", entity.getId(), updatedEntity.getId());
		Assert.assertEquals("failure - expected text attribute match", entity.getCountryId(),
				updatedEntity.getCountryId());

	}

	//@Test
	public void testDeleteData() throws Exception {

		// Create some test data
		String id = "US1960";

		// Perform the behavior being tested
		String uri = "/api/data/{id}";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(uri, id)).andReturn();
		
		// Extract the response status and body
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// Verify the GreetingService.delete method was invoked once
		verify(dataService, times(1)).delete(id);

		// Perform standard JUnit assertions on the test results
		Assert.assertEquals("failure - expected HTTP status 204", 204, status);
		Assert.assertTrue("failure - expected HTTP response body to be empty", content.trim().length() == 0);

	}

	private Collection<Data> getEntityListStubData() {
		Collection<Data> list = new ArrayList<Data>();
		list.add(getEntityStubData());
		return list;
	}

	private Data getEntityStubData() {
		Data entity = new Data();
		entity.setCountryId("US");
		entity.setDate(1961);
		entity.setId("US1961");
		return entity;
	}

}
