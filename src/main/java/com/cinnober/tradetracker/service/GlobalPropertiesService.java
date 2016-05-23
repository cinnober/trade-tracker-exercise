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
package com.cinnober.tradetracker.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.cinnober.tradetracker.core.GlobalProperties;
import com.cinnober.tradetracker.dao.GlobalPropertiesDAO;
import com.cinnober.tradetracker.exception.ValidationException;
import com.cinnober.tradetracker.util.DateTimeUtil;
import com.cinnober.tradetracker.viewobjects.GlobalPropertiesDetails;
import com.google.common.base.Optional;

/**
 * Service class  for {@link GlobalProperties} related request.
 * 
 * @author suresh
 *
 */
public class GlobalPropertiesService {
	
    private final GlobalPropertiesDAO dao;
	
	public GlobalPropertiesService(GlobalPropertiesDAO dao) {
		this.dao = dao;
	}

	/**
	 * There can be only one row in global properties. If one record already
	 * available in the system then all future add requests will update the date
	 * in the global properties.
	 * 
	 * @param prop
	 *            {@link GlobalProperties}
	 * @return the stored global properties
	 * @throws ValidationException
	 */
	public GlobalPropertiesDetails addGlobalProperties(GlobalProperties prop) throws ValidationException {
		List<GlobalProperties> props = dao.findAll();
		if (props != null && props.size() > 0) {
			props.forEach(p -> {
				p.setBusinessDate(prop.getBusinessDate());
				dao.create(p);
			});
			return new GlobalPropertiesDetails(props.get(0).getId(), DateTimeUtil.getDateString(prop.getBusinessDate()));
		}
		GlobalProperties dbObj = dao.create(prop);
		return new GlobalPropertiesDetails(dbObj.getId(), DateTimeUtil.getDateString(dbObj.getBusinessDate()));
	}
	
	/**
	 * @param id
	 * @return the {@link GlobalProperties} for the input id,
	 */
	public GlobalProperties getGlobalProperties(Long id) {
		Optional<GlobalProperties> prop = dao.findById(id);
		if (prop.isPresent()) {
			return prop.get();
		}
		return null;
	}
	
	/**
	 * @return all {@link GlobalProperties} available in the system. There will
	 *         be maximum one global properties available in the system. If no
	 *         record available then an empty list is returned.
	 */
	public List<GlobalPropertiesDetails> getAllGlobalProperties() {
		List<GlobalProperties> props = dao.findAll();
		if(props != null && props.size() > 0) {
			return props.stream()
					.filter(p -> p != null)
					.map(p -> new GlobalPropertiesDetails(p.getId(),
							DateTimeUtil.getDateString(p.getBusinessDate())))
					.collect(Collectors.toList());
		}		
		return new ArrayList<>();
	}
	
}