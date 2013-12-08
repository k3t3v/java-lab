package org.mpavel.app.views;

import org.mpavel.app.security.ApplicationSecurity;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class ApplicationView extends Panel implements View
{
	private static final long serialVersionUID = -1307009854319440228L;
	public static final String NAME = "ApplicationView";

	public ApplicationView()
	{	
		VerticalLayout layout = new VerticalLayout();
		Link lnk = new Link("Count", new ExternalResource("#!" + CountView.NAME));
		layout.addComponent(lnk);

		lnk = new Link("Message: Hello", new ExternalResource("#!" + MessageView.NAME + "/Hello"));
		layout.addComponent(lnk);

		lnk = new Link("Message: Bye", new ExternalResource("#!" + MessageView.NAME + "/Bye/Goodbye"));
		layout.addComponent(lnk);

		@SuppressWarnings("serial")
		Button logout = new Button("Logout", new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				ApplicationSecurity.logout();
				UI.getCurrent().getNavigator().navigateTo(LoginView.NAME);
			}
		});
		layout.addComponent(logout);
		setContent(layout);
	}

	@Override
	public void enter(ViewChangeEvent event)
	{
	}
}
