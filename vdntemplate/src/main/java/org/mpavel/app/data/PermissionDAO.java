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

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.mpavel.app.domain.Permission;
import org.mpavel.app.utils.ApplicationLogger;

import com.google.inject.TypeLiteral;

/**
 * I...
 * 
 * @author mpavel
 * 
 */
public class PermissionDAO extends HibernateGenericDAO<Permission> {

	private final static ApplicationLogger logger = new ApplicationLogger(
			PermissionDAO.class);

	/**
	 * @param type
	 */
	public PermissionDAO(TypeLiteral<Permission> type) {
		super(type);
	}

	public Permission insertPermission(Session session, String permissionName) {
		logger.executionTrace();

		final Query query = session
				.createSQLQuery(
						"select * from Permission x where x.permissionName = :zzz")
				.addEntity(Permission.class)
				.setParameter("zzz", permissionName);

		final List<?> result = query.list();

		if (result.size() > 0)
			return null;

		final Permission permission = new Permission(permissionName);
		final Serializable entityId = session.save(permission);

		return (Permission) session.get(Permission.class, entityId);
	}
}
