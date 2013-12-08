/**
 * [Copyright information]
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
