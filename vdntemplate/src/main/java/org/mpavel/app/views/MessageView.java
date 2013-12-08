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

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

/**
 * I...
 *
 * @author mpavel
 *
 */
public class MessageView extends Panel implements View
{
	private static final long serialVersionUID = -7650094489274659106L;
	public static final String NAME = "MessageView";

	public MessageView()
	{
		setCaption("Messages");
	}

	@Override
	public void enter(ViewChangeEvent event)
	{
		if (event.getParameters() != null)
		{
			String[] msgs = event.getParameters().split("/");
			for (String msg : msgs)
			{
				setContent(new Label(msg));
			}
		}
	}
}
