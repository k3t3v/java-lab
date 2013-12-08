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
package org.mpavel.app.data;

import org.mpavel.app.domain.Account;
import org.mpavel.app.domain.Permission;
import org.mpavel.app.domain.Role;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;

/**
 * I...
 * 
 * @author mpavel
 * 
 */
public class DataModule extends AbstractModule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		bind(new TypeLiteral<GenericDAO<Account>>() {
		}).to(new TypeLiteral<HibernateGenericDAO<Account>>() {
		}).in(Scopes.SINGLETON);
		
		bind(new TypeLiteral<GenericDAO<Role>>() {
		}).to(new TypeLiteral<HibernateGenericDAO<Role>>() {
		}).in(Scopes.SINGLETON);
		
		bind(new TypeLiteral<GenericDAO<Permission>>() {
		}).to(new TypeLiteral<HibernateGenericDAO<Permission>>() {
		}).in(Scopes.SINGLETON);

	}

}
