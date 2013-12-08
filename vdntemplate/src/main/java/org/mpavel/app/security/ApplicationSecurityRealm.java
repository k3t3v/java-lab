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

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.hibernate.Query;
import org.hibernate.Session;
import org.mpavel.app.data.DatabaseUtil;
import org.mpavel.app.domain.Account;
import org.mpavel.app.domain.Permission;
import org.mpavel.app.domain.Role;
import org.mpavel.app.utils.ApplicationLogger;

/**
 * I...
 *
 * @author mpavel
 *
 */
public class ApplicationSecurityRealm extends JdbcRealm
{
	private static final ApplicationLogger logger = new ApplicationLogger(ApplicationSecurityRealm.class);

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException
	{
		logger.executionTrace();

		final UsernamePasswordToken authToken = (UsernamePasswordToken) token;

		final SimpleAuthenticationInfo authInfo = new SimpleAuthenticationInfo(
				authToken.getUsername(),
				authToken.getPassword(),
				getName());

		return authInfo;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
	{
		logger.executionTrace();

		if (principals.fromRealm(getName()).isEmpty())
			return null;

		final String username = (String) principals.fromRealm(getName()).iterator().next();
		final Account account = getAccount(username);

		if (account == null)
			return null;

		final SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

		for (Role role : account.getRoles())
			authorizationInfo.addRole(role.getRoleName());

		return authorizationInfo;
	}

	@Override
	protected Set<String> getRoleNamesForUser(Connection conn, String username) throws SQLException
	{
		logger.executionTrace();

		final Set<String> roleNames = new HashSet<String>();
		final Account account = getAccount(username);

		if (account != null)
			for (Role role : account.getRoles())
				roleNames.add(role.getRoleName());

		return roleNames;
	}

	@Override
	protected Set<String> getPermissions(Connection conn, String username, Collection<String> roleNames)
			throws SQLException
	{
		logger.executionTrace();

		final Set<String> permissionNames = new HashSet<String>();

		for (final String roleName : roleNames)
		{
			final Role role = getRole(roleName);

			if (role != null)
				for (final Permission permission : role.getPermissions())
					permissionNames.add(permission.getPermissionName());
		}

		return permissionNames;
	}

	private Account getAccount(String username)
	{
		logger.executionTrace();

		final Session session = DatabaseUtil.getSessionFactory().getCurrentSession();

		final Query query = session.createSQLQuery(
				"select * from Account x where x.username = :zzz")
				.addEntity(Account.class)
				.setParameter("zzz", username);

		return (Account) query.uniqueResult();
	}

	private Role getRole(String roleName)
	{
		logger.executionTrace();

		final Session session = DatabaseUtil.getSessionFactory().getCurrentSession();

		final Query query = session.createSQLQuery(
				"select * from Role x where x.roleName = :zzz")
				.addEntity(Role.class)
				.setParameter("zzz", roleName);

		return (Role) query.uniqueResult();
	}
}
