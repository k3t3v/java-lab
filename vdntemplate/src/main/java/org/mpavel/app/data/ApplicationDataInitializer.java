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

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.mpavel.app.domain.Account;
import org.mpavel.app.domain.Permission;
import org.mpavel.app.domain.Role;
import org.mpavel.app.utils.ApplicationLogger;

import com.google.inject.Inject;

/**
 * I...
 * 
 * @author mpavel
 * 
 */
public class ApplicationDataInitializer {
	
	private final static ApplicationLogger logger = new ApplicationLogger(ApplicationDataInitializer.class);
	
	private static GenericDAO<Account> accountDAO;
	
	private static GenericDAO<Role> roleDAO;
	
	private static GenericDAO<Permission> permissionDAO;
	
	/**
	 * 
	 */
	@Inject
	public ApplicationDataInitializer(GenericDAO<Account> accountDAO, GenericDAO<Role> roleDAO, GenericDAO<Permission> permissionDAO) {
		ApplicationDataInitializer.accountDAO = accountDAO;
		ApplicationDataInitializer.roleDAO = roleDAO;
		ApplicationDataInitializer.permissionDAO = permissionDAO;
	}
	
	public static void insertAuthData() {
		logger.executionTrace();
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		try {
			Set<Permission> permissions = new HashSet<Permission>();
			permissions.add(((PermissionDAO) permissionDAO).insertPermission(session, "Permission 1"));
			permissions.add(((PermissionDAO) permissionDAO).insertPermission(session, "Permission 2"));
			permissions.add(((PermissionDAO) permissionDAO).insertPermission(session, "Permission 3"));

			Set<Role> roles = new HashSet<Role>();
			roles.add(((RoleDAO) roleDAO).insertRole(session, "Role 1", permissions));

			((AccountDAO) accountDAO).insertAccount(session, "admin", "admin", roles);

			permissions.clear();
			permissions.add(((PermissionDAO) permissionDAO).insertPermission(session, "Permission 4"));
			permissions.add(((PermissionDAO) permissionDAO).insertPermission(session, "Permission 5"));
			permissions.add(((PermissionDAO) permissionDAO).insertPermission(session, "Permission 6"));

			roles.clear();
			roles.add(((RoleDAO) roleDAO).insertRole(session, "Role 2", permissions));

			((AccountDAO) accountDAO).insertAccount(session, "guest", "guest", roles);

			permissions.clear();
			permissions.add(((PermissionDAO) permissionDAO).insertPermission(session, "Permission 7"));
			permissions.add(((PermissionDAO) permissionDAO).insertPermission(session, "Permission 8"));
			permissions.add(((PermissionDAO) permissionDAO).insertPermission(session, "Permission 9"));

			roles.clear();
			roles.add(((RoleDAO) roleDAO).insertRole(session, "Role 3", permissions));

			((AccountDAO) accountDAO).insertAccount(session, "member", "member", roles);
		} finally {
			session.getTransaction().commit();
		}
	}
}
