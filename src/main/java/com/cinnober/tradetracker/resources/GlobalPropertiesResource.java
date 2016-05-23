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
package com.cinnober.tradetracker.resources;

import io.dropwizard.hibernate.UnitOfWork;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.cinnober.tradetracker.core.GlobalProperties;
import com.cinnober.tradetracker.service.GlobalPropertiesService;
import com.cinnober.tradetracker.viewobjects.GlobalPropertiesDetails;

/**
 * This class is the entry point for {@link GlobalProperties} related requests.
 * 
 * @author suresh
 *
 */
@Path("/globalproperties")
@Produces(MediaType.APPLICATION_JSON)
public class GlobalPropertiesResource {

	private final GlobalPropertiesService service;

	public GlobalPropertiesResource(GlobalPropertiesService service) {
		this.service = service;
	}

	@POST
	@UnitOfWork
	public GlobalPropertiesDetails createGlobalProperties(GlobalProperties prop) {
		return service.addGlobalProperties(prop);
	}

	@GET
	@UnitOfWork
	public List<GlobalPropertiesDetails> listGlobalProperties() {
		return service.getAllGlobalProperties();
	}
}
