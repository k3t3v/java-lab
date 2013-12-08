package org.mpavel.app.views;

import org.apache.shiro.authz.annotation.RequiresAuthentication;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

@RequiresAuthentication
public class CountView extends Panel implements View
{
	private static final long serialVersionUID = -2117139212682899698L;
	public static final String NAME = "CountView";
	private static int count = 1;

	public CountView()
	{
		setContent(new Label("Created: " + count++));
	}

	@Override
	public void enter(ViewChangeEvent event)
	{
	}
}