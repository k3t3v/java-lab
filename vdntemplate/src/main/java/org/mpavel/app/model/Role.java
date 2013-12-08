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
package org.mpavel.app.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.UniqueConstraint;

import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * I...
 *
 * @author mpavel
 *
 */
@Entity
@Table(name = "Role", uniqueConstraints = { @UniqueConstraint(columnNames = "roleName") })
public class Role
{
	private String id;
	private String roleName;
	private String description;
	private Set<Account> accounts = new HashSet<Account>(0);
	private Set<Permission> permissions = new HashSet<Permission>(0);

	public Role()
	{
	}

	public Role(String roleName)
	{
		this.roleName = roleName;
	}

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name = "id", unique = true, nullable = false)
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	@Column(name = "roleName", unique = true, nullable = false)
	public String getRoleName()
	{
		return roleName;
	}

	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}

	@Column(name = "description", nullable = true)
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
	public Set<Account> getAccounts()
	{
		return accounts;
	}

	public void setAccounts(Set<Account> accounts)
	{
		this.accounts = accounts;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "RolePermission",
			joinColumns = { @JoinColumn(name = "roleId", nullable = false, updatable = false) },
			inverseJoinColumns = { @JoinColumn(name = "permissionId", nullable = false, updatable = false) })
	public Set<Permission> getPermissions()
	{
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions)
	{
		this.permissions = permissions;
	}
}
