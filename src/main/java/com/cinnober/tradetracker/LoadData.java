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
package com.cinnober.tradetracker;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cinnober.tradetracker.core.GlobalProperties;
import com.cinnober.tradetracker.util.DateTimeUtil;
import com.cinnober.tradetracker.viewobjects.GlobalPropertiesDetails;

/**
 * This is used to load test data on a newly installed system.
 * 
 * @author suresh
 *
 */
public class LoadData {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoadData.class);

	public static void main(String[] pArgs) throws Exception {		
		LoadData obj = new LoadData();
		Client client = ClientBuilder.newClient();
		obj.addGlobalProperties(client);
		client.close();
		LOGGER.info("Finished loading data");
	}
	
	public void addGlobalProperties(Client client) throws Exception {
		
		List<GlobalPropertiesDetails> props = client.target("http://localhost:8080/globalproperties").request()
				.get(new GenericType<List<GlobalPropertiesDetails>>() {});		
		if (props != null && props.size() > 0) {
			LOGGER.info("Global properties already exist");
			return;
		}

		final GlobalProperties prop = new GlobalProperties(DateTimeUtil.getCurrentSystemDate());
		client.target("http://localhost:8080/globalproperties").request()
				.post(Entity.entity(prop, MediaType.APPLICATION_JSON_TYPE)).readEntity(GlobalPropertiesDetails.class);
		LOGGER.info("inserted global properties " + prop.getId());
	}
}
