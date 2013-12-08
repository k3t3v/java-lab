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
package org.mpavel.app.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.mpavel.app.security.ApplicationSecurityModule;
import org.mpavel.app.security.ApplicationSecurityRealm;
import org.mpavel.app.utils.ApplicationLogger;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceFilter;

/**
 * I...
 *
 * @author mpavel
 *
 */
@WebFilter(urlPatterns={"/*"})
public class ApplicationFilter extends GuiceFilter
{
	private static final ApplicationLogger logger = new ApplicationLogger(ApplicationFilter.class);
	private static Injector applicationInjector;
	private static Injector securityInjector;

	public static Injector getApplicationInjector()
	{
		logger.executionTrace();
		return applicationInjector;
	}

	public static Injector getSecurityInjector()
	{
		logger.executionTrace();
		return securityInjector;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException
	{
		logger.executionTrace();
		super.doFilter(request, response, chain);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{
		logger.executionTrace();

		if (applicationInjector != null)
			throw new ServletException("application injector already created");

		if (securityInjector != null)
			throw new ServletException("security injector already created");

		 final Realm realm = new ApplicationSecurityRealm();
		 final SecurityManager securityManager = new DefaultSecurityManager(realm);
		 SecurityUtils.setSecurityManager(securityManager);
		
		applicationInjector = Guice.createInjector(new ApplicationModule());
		securityInjector = Guice.createInjector(new ApplicationSecurityModule());

		super.init(filterConfig);
	}
}
