package org.mpavel.app.web;

import org.mpavel.app.ApplicationUI;
import org.mpavel.app.utils.ApplicationLogger;

import com.google.inject.Provides;
import com.google.inject.name.Names;
import com.google.inject.servlet.ServletModule;
import com.vaadin.ui.UI;

public class ApplicationModule extends ServletModule
{
	private static final ApplicationLogger logger = new ApplicationLogger(ApplicationModule.class);
	
	@Override
	protected void configureServlets()
	{
		logger.executionTrace();
		serve("/*").with(ApplicationServlet.class);
		bind(String.class).annotatedWith(Names.named("title")).toInstance("Vaadin Application");
		bind(String.class).annotatedWith(Names.named("version")).toInstance("<b>Vaadin 7.1.8</b>");
	}

	@Provides
	private Class<? extends UI> provideUIClass()
	{
		logger.executionTrace();
		return ApplicationUI.class;
	}
}
