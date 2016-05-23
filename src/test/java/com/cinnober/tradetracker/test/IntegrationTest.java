/*
 * Copyright (c) 2013 Cinnober Financial Technology AB, Stockholm,
 * Sweden. All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Cinnober Financial Technology AB, Stockholm, Sweden. You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Cinnober.
 *
 * Cinnober makes no representations or warranties about the suitability
 * of the software, either expressed or implied, including, but not limited
 * to, the implied warranties of merchantibility, fitness for a particular
 * purpose, or non-infringement. Cinnober shall not be liable for any
 * damages suffered by licensee as a result of using, modifying, or
 * distributing this software or its derivatives.
 */
package com.cinnober.tradetracker.test;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.cinnober.tradetracker.core.GlobalProperties;
import com.cinnober.tradetracker.util.DateTimeUtil;
import com.cinnober.tradetracker.viewobjects.GlobalPropertiesDetails;

/**
 * These tests should be run when server is up and running.
 * Set it to ignore while running 'mvn package'.
 * 
 * @author suresh
 *
 */
@Ignore("These tests should be run when server is up and running. "
		+ "Set it to ignore while running 'mvn package'.")
public class IntegrationTest {

    private Client client;
    private final String GLOBAL_PROP_URL = "http://localhost:8080/globalproperties";
    private final MediaType TYPE = MediaType.APPLICATION_JSON_TYPE;

    @Before
    public void setUp() throws Exception {
        client = ClientBuilder.newClient();
    }

    @After
    public void tearDown() throws Exception {
        client.close();
    }
    
    @Test
    public void addGlobalProperties() {
		GlobalProperties prop = new GlobalProperties(DateTimeUtil.getCurrentSystemDate());
		client.target(GLOBAL_PROP_URL).request().post(Entity.entity(prop, TYPE)).readEntity(GlobalPropertiesDetails.class);
		List<GlobalPropertiesDetails> props = client.target(GLOBAL_PROP_URL).request().get(new GenericType<List<GlobalPropertiesDetails>>() {});
		Assert.assertNotNull(props);
		Assert.assertEquals(DateTimeUtil.getEpochDate(props.get(0).getBusinessDate()), prop.getBusinessDate());
	}
    
    @Test
    public void incrementBusinessDateByOneDay() {
		// Set business date to current system date
		GlobalProperties prop = new GlobalProperties(DateTimeUtil.getCurrentSystemDate());
		client.target(GLOBAL_PROP_URL).request().post(Entity.entity(prop, TYPE)).readEntity(GlobalPropertiesDetails.class);
		List<GlobalPropertiesDetails> props = client.target(GLOBAL_PROP_URL).request().get(new GenericType<List<GlobalPropertiesDetails>>() {});
		Assert.assertNotNull(props);
		Assert.assertEquals(DateTimeUtil.getEpochDate(props.get(0).getBusinessDate()), prop.getBusinessDate());
		
		// increase date by 1 day
		GlobalProperties prop1 = new GlobalProperties(DateTimeUtil.getEpochDate(props.get(0).getBusinessDate()) + 1);
		client.target(GLOBAL_PROP_URL).request().post(Entity.entity(prop1, TYPE)).readEntity(GlobalPropertiesDetails.class);
		List<GlobalPropertiesDetails> props1 = client.target(GLOBAL_PROP_URL).request().get(new GenericType<List<GlobalPropertiesDetails>>() {});
		Assert.assertNotNull(props1);
		Assert.assertEquals(DateTimeUtil.getEpochDate(props1.get(0).getBusinessDate()), prop1.getBusinessDate());
	}
}
