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
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.mpavel.app.domain.Permission;
import org.mpavel.app.domain.Role;
import org.mpavel.app.utils.ApplicationLogger;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;

/**
 * I...
 * 
 * @author mpavel
 * 
 */
public class RoleDAO extends HibernateGenericDAO<Role> {

	private final static ApplicationLogger logger = new ApplicationLogger(
			RoleDAO.class);
	
	/**
	 * @param type
	 */
	@Inject
	public RoleDAO(TypeLiteral<Role> type) {
		super(type);
	}

	public Role insertRole(Session session, String roleName,
			Set<Permission> permissions) {
		logger.executionTrace();

		final Query query = session
				.createSQLQuery("select * from Role x where x.roleName = :zzz")
				.addEntity(Role.class).setParameter("zzz", roleName);

		final List<?> result = query.list();

		if (result.size() > 0)
			return null;

		final Role role = new Role(roleName);
		role.setPermissions(permissions);

		final Serializable entityId = session.save(role);
		return (Role) session.get(Role.class, entityId);
	}

}
