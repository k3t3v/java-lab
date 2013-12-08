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
package org.mpavel.app.views;

import org.mpavel.app.security.ApplicationSecurity;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * I...
 *
 * @author mpavel
 *
 */
public class LoginView extends Panel implements View
{
	private static final long serialVersionUID = 6151712847910060303L;
	public static final String NAME = "LoginView";

	public LoginView()
	{
		this(null);
	}

	public LoginView(final String fragmentAndParameters)
	{
		setCaption("Login");
		
		VerticalLayout layout = new VerticalLayout();
		final TextField username = new TextField("Username");
		layout.addComponent(username);

		final PasswordField password = new PasswordField("Password");
		layout.addComponent(password);

		final CheckBox rememberMe = new CheckBox("Remember Me");
		layout.addComponent(rememberMe);

		username.focus();
		
		// TODO: Remove these two lines before production release
		username.setValue("admin");
		password.setValue("admin");
		
		if (ApplicationSecurity.isRemembered())
		{
			username.setValue(ApplicationSecurity.whoIsRemembered());
			rememberMe.setValue(ApplicationSecurity.isRemembered());
			password.focus();
		}

		@SuppressWarnings("serial")
		final Button login = new Button("Login", new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				final Navigator navigator = UI.getCurrent().getNavigator();
				if (ApplicationSecurity.login(username.getValue(), password.getValue(), rememberMe.getValue()))
				{
					final String location = (fragmentAndParameters == null)
							? ApplicationView.NAME
							: fragmentAndParameters;
					
					navigator.navigateTo(location);
				}
				else
				{
					navigator.navigateTo(LoginView.NAME);
				}
			}
		});
		layout.addComponent(login);
		setContent(layout);
	}

	@Override
	public void enter(ViewChangeEvent event)
	{
	}
}

