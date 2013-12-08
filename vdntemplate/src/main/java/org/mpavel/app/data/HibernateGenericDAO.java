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

import org.hibernate.Session;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;

/**
 * I...
 * 
 * @author mpavel
 * @param <T>
 * 
 */
public class HibernateGenericDAO<T> implements GenericDAO<T> {

	protected final Class<T> clazz;

	@Inject
	@SuppressWarnings("unchecked")
	public HibernateGenericDAO(TypeLiteral<T> type) {
		this.clazz = (Class<T>) type.getRawType();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mpavel.app.data.GenericDAO#get(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T get(int id) {
		return (T) getSession().get(clazz, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mpavel.app.data.GenericDAO#save(java.lang.Object)
	 */
	@Override
	public void save(T entity) {
		getSession().persist(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mpavel.app.data.GenericDAO#update(java.lang.Object)
	 */
	@Override
	public void update(T entity) {
		getSession().update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mpavel.app.data.GenericDAO#delete(java.lang.Object)
	 */
	@Override
	public void delete(T entity) {
		getSession().delete(entity);
	}

	protected Session getSession() {
		return HibernateUtil.getSessionFactory().getCurrentSession();
	}

}
