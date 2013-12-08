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
package org.mpavel.app;

import org.mpavel.app.orm.DatabaseUtil;
import org.mpavel.app.security.ApplicationSecurity;
import org.mpavel.app.utils.ApplicationLogger;
import org.mpavel.app.views.ApplicationView;
import org.mpavel.app.views.LoginView;
import org.mpavel.app.web.ApplicationFilter;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

/**
 * I...
 *
 * @author mpavel
 *
 */
@Theme("mytheme")
public class ApplicationUI extends UI {
    	private static final ApplicationLogger logger = new ApplicationLogger(ApplicationUI.class);
    	public static final String LOGINVIEW = LoginView.NAME;
    	
    	@Inject
    	@Named("title")
    	private String title = "Vaadin Application UI (default title)";

    	@Inject(optional = true)
    	@Named("version")
    	private String version = "Vaadin <i>version unknown</i>";

    	@Override
    	public void init(VaadinRequest request)
    	{
    		logger.executionTrace();
    		DatabaseUtil.getSessionFactory(); // Initialize...

    		final Navigator navigator = new Navigator(this, this);
    		setNavigator(navigator);
    		
    		navigator.addProvider(new ViewProvider()
    		{
    			private static final long serialVersionUID = -3308179049710571790L;

    			@Override
    			public String getViewName(String viewAndParameters)
    			{
    				if (viewAndParameters == null || viewAndParameters.length() == 0)
    					return ApplicationView.class.getSimpleName();
    				
    				String[] parts = viewAndParameters.split("/");
    				return parts[0];
    			}
    			
    			@Override
    			public View getView(String viewName)
    			{
    				final Injector injector = ApplicationFilter.getSecurityInjector();
    				final String packageName = ApplicationView.class.getPackage().getName();
    				Class<?> classType = ApplicationView.class;

    				try
    				{
    					classType = Class.forName(packageName + "." + viewName);
    				}
    				catch (ClassNotFoundException e)
    				{
    				}
    				
    				return (View) injector.getInstance(classType);
    			}
    		});
    		
    		final String viewName = (ApplicationSecurity.isAuthenticated())
    				? ApplicationView.NAME
    				: LoginView.NAME;

    		navigator.navigateTo(viewName);
    	}
}
