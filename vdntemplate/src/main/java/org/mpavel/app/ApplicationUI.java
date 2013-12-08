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
