/**
 * Copyright 2013 Muhammad Pavel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package org.mpavel.app.web;

import java.util.Properties;

import javax.servlet.annotation.WebServlet;

import org.mpavel.app.ApplicationProvider;
import org.mpavel.app.ApplicationUI;
import org.mpavel.app.utils.ApplicationLogger;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.DeploymentConfiguration;
import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.VaadinServlet;

/**
 * I...
 * 
 * @author asus
 * 
 */
@Singleton
@VaadinServletConfiguration(productionMode = false, ui = ApplicationUI.class, widgetset = "org.mpavel.app.AppWidgetSet")
public class ApplicationServlet extends VaadinServlet implements SessionInitListener {
	private static final ApplicationLogger logger = new ApplicationLogger(ApplicationServlet.class);

	@Inject
	private ApplicationProvider applicationProvider;
	
	@Override
	public void sessionInit(SessionInitEvent event) throws ServiceException
	{
		logger.executionTrace();
		event.getSession().addUIProvider(applicationProvider);
	}
	
	@Override
	protected DeploymentConfiguration createDeploymentConfiguration(Properties initParameters)
	{
		logger.executionTrace();
		initParameters.setProperty(SERVLET_PARAMETER_PRODUCTION_MODE, "true");
		return super.createDeploymentConfiguration(initParameters);
	}

	@Override
	protected void servletInitialized()
	{
		logger.executionTrace();
		getService().addSessionInitListener(this);
	}
}
