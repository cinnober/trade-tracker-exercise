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

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

import java.util.Map;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import com.cinnober.tradetracker.auth.TradeTrackerAuthenticator;
import com.cinnober.tradetracker.auth.TradeTrackerAuthorizer;
import com.cinnober.tradetracker.cli.RenderCommand;
import com.cinnober.tradetracker.core.GlobalProperties;
import com.cinnober.tradetracker.core.Template;
import com.cinnober.tradetracker.core.User;
import com.cinnober.tradetracker.dao.GlobalPropertiesDAO;
import com.cinnober.tradetracker.health.TemplateHealthCheck;
import com.cinnober.tradetracker.resources.GlobalPropertiesResource;
import com.cinnober.tradetracker.resources.ViewResource;
import com.cinnober.tradetracker.service.GlobalPropertiesService;

/**
 * This class loads the configuration and required resources for the system.
 * 
 * @author suresh
 *
 */
public class TradeTrackerApplication extends
		Application<TradeTrackerConfiguration> {
	public static void main(String[] args) throws Exception {
		new TradeTrackerApplication().run(args);
	}

	private final HibernateBundle<TradeTrackerConfiguration> hibernateBundle = new HibernateBundle<TradeTrackerConfiguration>(
			User.class, GlobalProperties.class) {
		@Override
		public DataSourceFactory getDataSourceFactory(
				TradeTrackerConfiguration configuration) {
			return configuration.getDataSourceFactory();
		}
	};

	@Override
	public String getName() {
		return "trade-tracker";
	}

	@Override
	public void initialize(Bootstrap<TradeTrackerConfiguration> bootstrap) {
		// Enable variable substitution with environment variables
		bootstrap
				.setConfigurationSourceProvider(new SubstitutingSourceProvider(
						bootstrap.getConfigurationSourceProvider(),
						new EnvironmentVariableSubstitutor(false)));

		bootstrap.addCommand(new RenderCommand());
		bootstrap.addBundle(new AssetsBundle());
		bootstrap.addBundle(new MigrationsBundle<TradeTrackerConfiguration>() {
			@Override
			public DataSourceFactory getDataSourceFactory(
					TradeTrackerConfiguration configuration) {
				return configuration.getDataSourceFactory();
			}
		});
		bootstrap.addBundle(hibernateBundle);
		bootstrap.addBundle(new ViewBundle<TradeTrackerConfiguration>() {
			@Override
			public Map<String, Map<String, String>> getViewConfiguration(
					TradeTrackerConfiguration configuration) {
				return configuration.getViewRendererConfiguration();
			}
		});
	}

	@Override
	public void run(TradeTrackerConfiguration configuration,
			Environment environment) {
		final GlobalPropertiesDAO propDao = new GlobalPropertiesDAO(
				hibernateBundle.getSessionFactory());
		final GlobalPropertiesService globalService = new GlobalPropertiesService(
				propDao);
		final Template template = configuration.buildTemplate();

		environment.healthChecks().register("template",
				new TemplateHealthCheck(template));
		environment.jersey().register(
				new AuthDynamicFeature(
						new BasicCredentialAuthFilter.Builder<User>()
								.setAuthenticator(
										new TradeTrackerAuthenticator())
								.setAuthorizer(new TradeTrackerAuthorizer())
								.setRealm("SUPER SECRET STUFF")
								.buildAuthFilter()));
		environment.jersey().register(
				new AuthValueFactoryProvider.Binder<>(User.class));
		environment.jersey().register(RolesAllowedDynamicFeature.class);
		environment.jersey().register(new ViewResource());
		environment.jersey().register(
				new GlobalPropertiesResource(globalService));
	}
}
