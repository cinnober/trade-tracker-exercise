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
package com.cinnober.tradetracker.dao;

import io.dropwizard.hibernate.AbstractDAO;

import java.util.List;

import org.hibernate.SessionFactory;

import com.cinnober.tradetracker.core.GlobalProperties;
import com.google.common.base.Optional;

/**
 * The data access object for the {@link GlobalProperties} object. This class is used to
 * store and retrieve the global properties data (i.e. the business date).
 * 
 * @author suresh
 *
 */
public class GlobalPropertiesDAO extends AbstractDAO<GlobalProperties> {
	
    public GlobalPropertiesDAO(SessionFactory factory) {
        super(factory);
    }

    /**
	 * @param id
	 * @return the global properties {@link GlobalProperties} information for
	 *         the input global properties id.
	 */
    public Optional<GlobalProperties> findById(Long id) {
        return Optional.fromNullable(get(id));
    }

    /**
	 * Stores the visit information.
	 * 
	 * @param prop {@link GlobalProperties}
	 * @return the stored {@link GlobalProperties} details.
	 */
    public GlobalProperties create(GlobalProperties prop) {
        return persist(prop);
    }

    /**
     * @return all {@link GlobalProperties} available in the system.
     */
    public List<GlobalProperties> findAll() {
        return list(namedQuery("GlobalProperties.findAll"));
    }
}
