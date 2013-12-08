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
@Table(name = "Permission", uniqueConstraints = { @UniqueConstraint(columnNames = "permissionName") })
public class Permission
{
	private String id;
	private String permissionName;
	private String description;
	private Set<Role> roles = new HashSet<Role>(0);

	public Permission()
	{
	}

	public Permission(String permissionName)
	{
		this.permissionName = permissionName;
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

	@Column(name = "permissionName", unique = true, nullable = false)
	public String getPermissionName()
	{
		return permissionName;
	}

	public void setPermissionName(String permissionName)
	{
		this.permissionName = permissionName;
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

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "permissions")
	public Set<Role> getRoles()
	{
		return roles;
	}

	public void setRoles(Set<Role> roles)
	{
		this.roles = roles;
	}
}
