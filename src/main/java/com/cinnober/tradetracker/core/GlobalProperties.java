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
package com.cinnober.tradetracker.core;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author suresh
 *
 */
@Entity
@Table(name = "globalproperties")
@NamedQueries({ @NamedQuery(
		name = "GlobalProperties.findAll", 
		query = "SELECT g FROM GlobalProperties g") })
public class GlobalProperties {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "businessDate", nullable = false)
	private int businessDate;

	
	public GlobalProperties() {

	}

	public GlobalProperties(int businessDate) {
		this.businessDate = businessDate;
	}

	public long getId() {
		return id;
	}

	public int getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(int businessDate) {
		this.businessDate = businessDate;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof GlobalProperties)) {
			return false;
		}

		final GlobalProperties that = (GlobalProperties) o;

		return Objects.equals(this.id, that.id)
				&& Objects.equals(this.businessDate, that.businessDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, businessDate);
	}

}
