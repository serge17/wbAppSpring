package com.example.service;

import java.util.Collection;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.example.AbstractTest;
import com.example.model.Data;

@Transactional
public class DataServiceTest extends AbstractTest {
	@Autowired
	private DataService service;

	@Before
	public void setUp() {
		service.evictCache();
	}

	@After
	public void tearDown() {
		// clean-up after execution of every test
	}

	@Test
	public void testFindAll() {
		Collection<Data> list = service.findAll();
		Assert.assertNotNull("failure - expected not null", list);
		Assert.assertEquals("failure - expected size", 14569, list.size());
	}

	@Test
	public void testFindOne() {

		String id = "US1960";

		Data entity = service.findOne(id);

		Assert.assertNotNull("failure - expected not null", entity);
		Assert.assertEquals("failure - expected id attribute match", id, entity.getId());

	}

	@Test
	public void testFindOneNotFound() {

		String id = "fadsf231";

		Data entity = service.findOne(id);

		Assert.assertNull("failure - expected null", entity);

	}

	@Test
	public void testCreate() {

		Data entity = new Data();
		entity.setCountryId("US");
		entity.setDate(1000);
		

		Data createdEntity = service.create(entity);

		Assert.assertNotNull("failure - expected not null", createdEntity);
		Assert.assertNotNull("failure - expected id attribute not null", createdEntity.getId());

		Collection<Data> list = service.findAll();

		Assert.assertEquals("failure - expected size", 14570, list.size());

	}

	//@Test
	public void testCreateWithId() {

		Exception exception = null;

		Data entity = new Data();
		entity.setId("ZW2016");
		entity.setDate(2016);
		entity.setCountryId("ZW");

		try {
			service.create(entity);
		} catch (Exception e) {
			exception = e;
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		Assert.assertNotNull("failure - expected exception", exception);
		Assert.assertTrue("failure - expected EntityExistsException", exception instanceof EntityExistsException);

	}

	@Test
	public void testUpdate() {

		String id = "US2000";
		Data entity = service.findOne(id);

		Assert.assertNotNull("failure - expected not null", entity);

		String updatedText = entity.getCountryValue() + " test";
		entity.setCountryValue(updatedText);
		Data updatedEntity = service.update(entity);

		Assert.assertNotNull("failure - expected not null", updatedEntity);
		Assert.assertEquals("failure - expected id attribute match", id, updatedEntity.getId());
		Assert.assertEquals("failure - expected text attribute match", updatedText, updatedEntity.getCountryValue());

	}

	//@Test
	public void testUpdateNotFound() {

		Exception exception = null;

		Data entity = new Data();
		entity.setId("AU1960");
		entity.setCountryValue("test");

		try {
			service.update(entity);
		} catch (NoResultException e) {
			exception = e;
		}

		Assert.assertNotNull("failure - expected exception", exception);
		Assert.assertTrue("failure - expected NoResultException", exception instanceof NoResultException);

	}

	@Test
	public void testDelete() {

		String id = "US1960";

		Data entity = service.findOne(id);

		Assert.assertNotNull("failure - expected not null", entity);

		service.delete(id);

		Collection<Data> list = service.findAll();

		Assert.assertEquals("failure - expected size", 14569, list.size());

		Data deletedEntity = service.findOne(id);

		Assert.assertNull("failure - expected null", deletedEntity);

	}
}
