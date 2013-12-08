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

import java.util.List;
import java.util.Set;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.hibernate.Query;
import org.hibernate.Session;
import org.mpavel.app.domain.Account;
import org.mpavel.app.domain.Role;
import org.mpavel.app.utils.ApplicationLogger;

/**
 * I...
 * 
 * @author mpavel
 * 
 */
public class AccountDAO {

	private final static ApplicationLogger logger = new ApplicationLogger(
			AccountDAO.class);
	
	public void insertAccount(Session session, String username,
			String password, Set<Role> roles) {
		logger.executionTrace();

		final Query query = session
				.createSQLQuery(
						"select * from Account x where x.username = :zzz")
				.addEntity(Account.class).setParameter("zzz", username);

		final List<?> result = query.list();

		if (result.size() == 0) {
			final Account account = new Account(username, password,
					"buddy@waddya.at");
			final RandomNumberGenerator random = new SecureRandomNumberGenerator();

			account.setSalt(random.nextBytes().toBase64());
			account.setPassword(new Sha256Hash(account.getPassword(), account
					.getSalt(), 1024).toBase64());

			session.save(account);
		}
	}

	public static boolean SaveAccount(Account account) {
		logger.executionTrace();

		final Session session = DatabaseUtil.getSessionFactory()
				.getCurrentSession();
		final Account existingAccount = getAccount(session,
				account.getUsername());

		if (existingAccount == null) {
			final RandomNumberGenerator random = new SecureRandomNumberGenerator();
			account.setSalt(random.nextBytes().toBase64());
			account.setPassword(new Sha256Hash(account.getPassword(), account
					.getSalt(), 1024).toBase64());
		} else {
			// Don't allow password changes during an account update.
			account.setPassword(existingAccount.getPassword());
		}

		if (!session.getTransaction().isActive())
			session.getTransaction().begin();

		session.saveOrUpdate(account);
		session.getTransaction().commit();

		return true;
	}

	private static Account getAccount(Session session, String username) {
		logger.executionTrace();

		final Query query = session
				.createSQLQuery(
						"select * from Account x where x.username = :zzz")
				.addEntity(Account.class).setParameter("zzz", username);

		return (Account) query.uniqueResult();
	}
}
