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
package org.mpavel.app.security;

import java.lang.reflect.Method;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.guice.ShiroModule;
import org.mpavel.app.utils.ApplicationLogger;

import com.google.inject.matcher.Matcher;
import com.google.inject.matcher.Matchers;

/**
 * I...
 *
 * @author mpavel
 *
 */
public class ApplicationSecurityModule extends ShiroModule
{
	private static final ApplicationLogger logger = new ApplicationLogger(ApplicationSecurityModule.class);

	@Override
	protected void configureShiro()
	{
		logger.executionTrace();

		try
		{
			final Matcher<? super Class<?>> classMatcher =
					Matchers.annotatedWith(RequiresAuthentication.class);

			final Matcher<? super Method> methodMatcher =
					Matchers.any();

			bindRealm().toConstructor(ApplicationSecurityRealm.class.getConstructor());
			bindInterceptor(classMatcher, methodMatcher, new ApplicationSecurityInterceptor());
		}
		catch (NoSuchMethodException e)
		{
			addError(e);
		}
	}
}
