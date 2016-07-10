package com.example.web.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.example.AbstractControllerTest;
import com.example.model.Data;
import com.example.service.DataService;

@Transactional
public class DataControllerTest extends AbstractControllerTest {
	@Autowired
	private DataService dataService;

	@Before
	public void setUp() {
		super.setUp();
		dataService.evictCache();
	}

	@Test
	public void testGetData() throws Exception {
		String uri = "/api/data";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		Assert.assertEquals("Failure - expected HTTP status 200", 200, status);
		Assert.assertTrue("Failure - expected http response body to have a value", content.trim().length() > 0);
	}

	@Test
	public void testGetDataOne() throws Exception {

		String uri = "/api/data/{id}";
		String id = "ZW2000";

		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.APPLICATION_JSON))
				.andReturn();

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		Assert.assertEquals("failure - expected HTTP status 200", 200, status);
		Assert.assertTrue("failure - expected HTTP response body to have a value", content.trim().length() > 0);

	}

	@Test
	public void testGetDataOneNotFound() throws Exception {

		String uri = "/api/data/{id}";
		String id = "SOMECOUNTRYID12";

		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.APPLICATION_JSON))
				.andReturn();

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		Assert.assertEquals("failure - expected HTTP status 404", 404, status);
		Assert.assertTrue("failure - expected HTTP response body to be empty", content.trim().length() == 0);

	}

	@Test
	public void testCreateData() throws Exception {

		String uri = "/api/data";
		Data data = new Data();
		data.setCountryId("AU");
		data.setDate(2000);
		String inputJson = super.mapToJson(data);

		MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		Assert.assertEquals("failure - expected HTTP status 201", 201, status);
		Assert.assertTrue("failure - expected HTTP response body to have a value", content.trim().length() > 0);

		Data createdData = super.mapFromJson(content, Data.class);
		
		Assert.assertNotNull("failure - expected greeting not null", createdData);
		Assert.assertNotNull("failure - expected greeting.id not null", createdData.getId());
		Assert.assertEquals("failure - expected greeting.text match", "AU", createdData.getCountryId());

	}

	@Test
	public void testUpdateData() throws Exception {

		String uri = "/api/data/{id}";
		String id = "US2001";
		Data data = dataService.findOne(id);
		String updatedIndicatorValue = data.getIndicatorValue() + " test";
		data.setIndicatorValue(updatedIndicatorValue);
		String inputJson = super.mapToJson(data);

		MvcResult result = mvc.perform(MockMvcRequestBuilders.put(uri, id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		Assert.assertEquals("failure - expected HTTP status 200", 200, status);
		Assert.assertTrue("failure - expected HTTP response body to have a value", content.trim().length() > 0);

		Data updatedData = super.mapFromJson(content, Data.class);

		Assert.assertNotNull("failure - expected greeting not null", updatedData);
		Assert.assertEquals("failure - expected greeting.id unchanged", data.getId(), updatedData.getId());
		Assert.assertEquals("failure - expected updated greeting text match", updatedIndicatorValue,
				updatedData.getIndicatorValue());

	}

	// @Test
	public void testDeleteData() throws Exception {

		String uri = "/api/data/{id}";
		String id = "ZW2000";

		MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(uri, id).contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		Assert.assertEquals("failure - expected HTTP status 204", 204, status);
		Assert.assertTrue("failure - expected HTTP response body to be empty", content.trim().length() == 0);

		Data deletedData = dataService.findOne(id);
		System.out.println(deletedData.getCountryValue());
		Assert.assertNull("failure - expected greeting to be null", deletedData);

	}
}
