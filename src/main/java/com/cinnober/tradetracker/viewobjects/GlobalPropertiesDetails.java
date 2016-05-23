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
package com.cinnober.tradetracker.viewobjects;

import com.cinnober.tradetracker.core.GlobalProperties;

/**
 * This is used to show results in GUI. The database object
 * {@link GlobalProperties} is converted to an object of this type because date
 * is converted from int to String for user readable purpose.
 * 
 * @author suresh
 *
 */
public class GlobalPropertiesDetails {

	private long id;

	private String businessDate;

	public GlobalPropertiesDetails() {

	}

	public GlobalPropertiesDetails(long id, String businessDate) {
		super();
		this.id = id;
		this.businessDate = businessDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}

	@Override
	public String toString() {
		return "GlobalPropertiesDetails [id=" + id + ", businessDate="
				+ businessDate + "]";
	}

}
