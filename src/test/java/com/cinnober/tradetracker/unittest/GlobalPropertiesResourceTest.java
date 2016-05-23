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
package com.cinnober.tradetracker.unittest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import io.dropwizard.testing.junit.ResourceTestRule;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.runners.MockitoJUnitRunner;

import com.cinnober.tradetracker.core.GlobalProperties;
import com.cinnober.tradetracker.dao.GlobalPropertiesDAO;
import com.cinnober.tradetracker.resources.GlobalPropertiesResource;
import com.cinnober.tradetracker.service.GlobalPropertiesService;
import com.cinnober.tradetracker.util.DateTimeUtil;
import com.cinnober.tradetracker.viewobjects.GlobalPropertiesDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableList;

/**
 * @author suresh
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class GlobalPropertiesResourceTest {
	private static final GlobalPropertiesDAO dao = mock(GlobalPropertiesDAO.class);
    private static final GlobalPropertiesService service = new GlobalPropertiesService(dao);
    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new GlobalPropertiesResource(service)).build();
    @Captor
    private ArgumentCaptor<GlobalProperties> userCaptor;
    private GlobalProperties prop;

    @Before
    public void setUp() {
    	prop = new GlobalProperties(DateTimeUtil.getCurrentSystemDate());
    }

    @After
    public void tearDown() {
        reset(dao);
    }

    @Test
    public void createGlobalProperties() throws JsonProcessingException {
    	when(dao.create(any(GlobalProperties.class))).thenReturn(prop);
        final Response response = resources.client().target("/globalproperties")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(prop, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        verify(dao).create(userCaptor.capture());
        assertThat(userCaptor.getValue()).isEqualTo(prop);
    }

    @Test
    public void getGlobalProperties() throws Exception {
        final ImmutableList<GlobalProperties> props = ImmutableList.of(prop);
        when(dao.findAll()).thenReturn(props);
        final List<GlobalPropertiesDetails> response = resources.client().target("/globalproperties")
                .request().get(new GenericType<List<GlobalPropertiesDetails>>() {});

        verify(dao).findAll();
		assertThat(DateTimeUtil.getEpochDate(response.get(0).getBusinessDate()))
				.isEqualTo(props.get(0).getBusinessDate());
    }
    
    
    @Test
    public void changeBusinessDate() throws JsonProcessingException {
    	when(dao.create(any(GlobalProperties.class))).thenReturn(prop);
        final Response response = resources.client().target("/globalproperties")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(prop, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        verify(dao).create(userCaptor.capture());
        assertThat(userCaptor.getValue()).isEqualTo(prop);
        
        // Change business date
        GlobalProperties newProp = new GlobalProperties(DateTimeUtil.getCurrentSystemDate() + 1);
        when(dao.create(any(GlobalProperties.class))).thenReturn(newProp);
        final Response newresponse = resources.client().target("/globalproperties")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(newProp, MediaType.APPLICATION_JSON_TYPE));
        assertThat(newresponse.getStatusInfo()).isEqualTo(Response.Status.OK);


        // verify businessdate
        final ImmutableList<GlobalProperties> props = ImmutableList.of(newProp);
        when(dao.findAll()).thenReturn(props);
        final List<GlobalPropertiesDetails> getResponse = resources.client().target("/globalproperties")
                .request().get(new GenericType<List<GlobalPropertiesDetails>>() {});

		assertThat(DateTimeUtil.getEpochDate(getResponse.get(0).getBusinessDate()))
				.isEqualTo(newProp.getBusinessDate());
    }

}
