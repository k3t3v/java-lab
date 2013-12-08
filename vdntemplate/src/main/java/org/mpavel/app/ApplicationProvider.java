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


import org.mpavel.app.utils.ApplicationLogger;
import org.mpavel.app.web.ApplicationFilter;

import com.google.inject.Inject;
import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UICreateEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.ui.UI;

/**
 * I...
 *
 * @author mpavel
 *
 */
public class ApplicationProvider extends UIProvider {
private static final ApplicationLogger logger = new ApplicationLogger(ApplicationProvider.class);
	
	@Inject
	private Class<? extends UI> applicationClass;

	@Override
	public UI createInstance(UICreateEvent event)
	{
		logger.executionTrace();
		return ApplicationFilter.getApplicationInjector().getProvider(applicationClass).get();
	}

	@Override
	public Class<? extends UI> getUIClass(UIClassSelectionEvent event)
	{
		logger.executionTrace();
		return applicationClass;
	}
}
