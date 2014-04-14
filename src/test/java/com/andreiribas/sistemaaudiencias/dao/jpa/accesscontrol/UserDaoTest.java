/** 
The MIT License (MIT)

Copyright (c) 2013 Andrei Gonçalves Ribas <andrei.g.ribas@gmail.com>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/

/**
 * 
 */
package com.andreiribas.sistemaaudiencias.dao.jpa.accesscontrol;


import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.andreiribas.sistemaaudiencias.cdi.junit.JpaDaoTest;
import com.andreiribas.sistemaaudiencias.cdi.junit.WeldJUnit4Runner;
import com.andreiribas.sistemaaudiencias.dao.accesscontrol.GroupDao;
import com.andreiribas.sistemaaudiencias.dao.accesscontrol.UserDao;
import com.andreiribas.sistemaaudiencias.model.AbstractEntity;
import com.andreiribas.sistemaaudiencias.model.accesscontrol.Group;
import com.andreiribas.sistemaaudiencias.model.accesscontrol.User;

/**
 * @author Andrei Gonçalves Ribas <andrei.g.ribas@gmail.com>
 *
 */
@RunWith(WeldJUnit4Runner.class)
public class UserDaoTest extends JpaDaoTest<UserDao, User> {
		
	@Inject
	private UserDao fixture;
	
	@Inject
	private GroupDao groupDao;
	
	@Test
	public void testSaveWhenAllDataIsOkShouldSucceed() {
		
		EntityManager entityManager = fixture.getEntityManager();
		
		User user = this.getDefaulModelInstanceForInsertion();
		
		this.setUpNewTransaction(entityManager);
		
		fixture.persist(user);
		
		this.commitTransaction(entityManager);
		
		TestCase.assertNotNull(user);
		
		TestCase.assertNotNull(user.getId());
		
	}
	
	@Test(expected = PersistenceException.class)
	public void testSaveWhenIdIsNullShouldThrowException() {
		
		EntityManager entityManager = fixture.getEntityManager();
		
		User user = this.getDefaulModelInstanceForInsertion();
		
		user.setId(null);
		
		this.setUpNewTransaction(entityManager);
		
		fixture.persist(user);
		
		this.commitTransaction(entityManager);
				
	}
	
	

	@Test
	public void testSaveWhenIdIsEmptyShouldSaveInstance() {
		
		EntityManager entityManager = fixture.getEntityManager();
		
		User user = this.getDefaulModelInstanceForInsertion();
		
		user.setId("");
		
		this.setUpNewTransaction(entityManager);
		
		fixture.persist(user);
		
		this.commitTransaction(entityManager);
		
		TestCase.assertNotNull(user);
		
		TestCase.assertNotNull(user.getId());
				
		TestCase.assertEquals("", user.getId());
	
	}
	
	@Test(expected = PersistenceException.class)
	public void testSaveWhenPasswordIsNullShouldThrowException() {
		
		EntityManager entityManager = fixture.getEntityManager();
		
		User user = this.getDefaulModelInstanceForInsertion();
		
		user.setPassword(null);
		
		this.setUpNewTransaction(entityManager);
		
		fixture.persist(user);
		
		this.commitTransaction(entityManager);
		
		TestCase.assertNotNull(user);
		
		TestCase.assertNotNull(user.getId());
		
	}
		
	@Test(expected = PersistenceException.class)
	public void testSaveWhenEmailIsNullShouldThrowException() {
		
		EntityManager entityManager = fixture.getEntityManager();
		
		User user = this.getDefaulModelInstanceForInsertion();
		
		user.setEmail(null);
		
		this.setUpNewTransaction(entityManager);
		
		fixture.persist(user);
		
		this.commitTransaction(entityManager);
		
		TestCase.assertNotNull(user);
		
		TestCase.assertNotNull(user.getId());
		
	}
	
	@Test(expected = PersistenceException.class)
	public void testSaveWhenIdAlreadyExistsShouldThrowException() {
		
		EntityManager entityManager = fixture.getEntityManager();
		
		User user = this.getDefaulModelInstanceForInsertion();
		
		this.setUpNewTransaction(entityManager);
		
		fixture.persist(user);
		
		this.commitTransaction(entityManager);
		
		TestCase.assertNotNull(user);
		
		TestCase.assertNotNull(user.getId());
		
		user = this.getDefaulModelInstanceForInsertion();
		
		this.setUpNewTransaction(entityManager);
		
		fixture.persist(user);
		
		this.commitTransaction(entityManager);
		
	}
	
	@Test(expected = PersistenceException.class)
	public void testSaveWhenEmailAlreadyExistsShouldThrowException() {
		
		EntityManager entityManager = fixture.getEntityManager();
		
		User user = this.getDefaulModelInstanceForInsertion();
		
		this.setUpNewTransaction(entityManager);
		
		fixture.persist(user);
		
		this.commitTransaction(entityManager);
		
		TestCase.assertNotNull(user);
		
		TestCase.assertNotNull(user.getId());
		
		user = this.getDefaulModelInstanceForInsertion();
		
		user.setId("lferna1986@gmail.com");
		
		this.setUpNewTransaction(entityManager);
		
		fixture.persist(user);
		
		this.commitTransaction(entityManager);
		
	}
	
	@Test(expected = PersistenceException.class)
	public void testSaveWhenCreationDateIsNullShouldThrowException() {
		
		EntityManager entityManager = fixture.getEntityManager();
		
		User user = this.getDefaulModelInstanceForInsertion();
		
		user.setCreationDate(null);
		
		this.setUpNewTransaction(entityManager);
		
		fixture.persist(user);
		
		this.commitTransaction(entityManager);
		
		TestCase.assertNotNull(user);
		
		TestCase.assertNotNull(user.getId());
		
	}
	
	@Test
	public void testSaveAndUpdateItsEmailWhenItsManaged() {
		
		EntityManager entityManager = fixture.getEntityManager();
		
		this.setUpNewTransaction(entityManager);
		
		long numberOfUsers = fixture.count(entityManager);
			
		TestCase.assertNotSame(-1L, numberOfUsers);
		
		User user = this.getDefaulModelInstanceForInsertion();
				
		fixture.persist(user);
		
		//new user is managed by jpa now.
		
		this.commitTransaction(entityManager);
		
		this.setUpNewTransaction(entityManager);
		
		String id = user.getId();
		
		TestCase.assertNotNull(id);
		
		user = fixture.find(id, entityManager);
		
		TestCase.assertNotNull(user);
		
		String newEmail = "andreiribas@yahoo.com.br";
		
		//user is updated without using merge, because it's already managed
		user.setEmail(newEmail);
		
		this.commitTransaction(entityManager);
		
		user = fixture.find(id, entityManager);
		
		TestCase.assertEquals(newEmail, user.getEmail());
		
		TestCase.assertEquals(numberOfUsers + 1, fixture.count(entityManager));
				
	}
	
	@Test
	public void testSaveAndUpdateItsNameWithMerge() {
		
		EntityManager entityManager = fixture.getEntityManager();
		
		this.setUpNewTransaction(entityManager);
		
		long numberOfUsers = fixture.count(entityManager);
			
		TestCase.assertNotSame(-1L, numberOfUsers);
		
		User user = this.getDefaulModelInstanceForInsertion();
		
		fixture.persist(user);
		
		this.commitTransaction(entityManager);
		
		this.setUpNewTransaction(entityManager);
		
		String id = user.getId();
		
		TestCase.assertNotNull(id);
		
		User userAtualizado = fixture.find(id, entityManager);
		
		String newEmail = "aaa@bbb.com";
		
		userAtualizado.setEmail(newEmail);
			
		//newUser is updated using merge, because it's already managed
		
		userAtualizado = fixture.merge(userAtualizado);
		
		TestCase.assertNotNull(userAtualizado);
		
		TestCase.assertEquals(newEmail, userAtualizado.getEmail());
		
		this.commitTransaction(entityManager);
		
		userAtualizado = fixture.find(id, entityManager);
		
		TestCase.assertEquals(newEmail, userAtualizado.getEmail());
		
		TestCase.assertEquals(numberOfUsers + 1, fixture.count(entityManager));
		
	}
	
	@Test
	public void testCreateNewUserAndDeleteIt() {
		
		EntityManager entityManager = fixture.getEntityManager();
		
		this.setUpNewTransaction(entityManager);
		
		long numberOfUsers = fixture.count(entityManager);
		
		User user = this.getDefaulModelInstanceForInsertion();
		
		fixture.persist(user);
		
		this.commitTransaction(entityManager);
		
		String id = user.getId();
		
		TestCase.assertNotNull(id);
		
		this.setUpNewTransaction(entityManager);
		
		User userAtualizado = fixture.find(id, entityManager);
		
		fixture.delete(userAtualizado);
		
		this.commitTransaction(entityManager);
		
		TestCase.assertEquals(numberOfUsers, fixture.count(entityManager));
		
	}
	
	@Test
	public void testCountWhenThereAreNoUsersShouldReturnZero() {
		TestCase.assertEquals(0, fixture.count());
	}
	
	@Test
	public void testCountWhenThereIsOnlyOneUserShouldReturnOne() {

		EntityManager entityManager = fixture.getEntityManager();
		
		this.setUpNewTransaction(entityManager);
		
		User user = this.getDefaulModelInstanceForInsertion();
		
		fixture.persist(user);
		
		this.commitTransaction(entityManager);
		
		TestCase.assertEquals(1, fixture.count(entityManager));
	
	}
	
	@Test
	public void testCountWhenThereAreMoreThanOneUsersShouldReturnNumberGreaterThanZero() {

		EntityManager entityManager = fixture.getEntityManager();
		
		this.setUpNewTransaction(entityManager);
		
		User user = this.getDefaulModelInstanceForInsertion();
		
		fixture.persist(user);
		
		User novoUser = this.getDefaulModelInstanceForInsertion();
		
		String userEmail = "lferna1986@gmail.com";
		
		novoUser.setId(userEmail);
		
		novoUser.setEmail(userEmail);
		
		fixture.persist(novoUser);
		
		this.commitTransaction(entityManager);
		
		TestCase.assertTrue(fixture.count(entityManager) > 1);
	
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testFindWhenIdIsNullShouldThrowException() {
		fixture.find(null);
	}
	
	@Test
	public void testFindWhenIdDoesNotExistShouldReturnNull() {
		TestCase.assertNull(fixture.find("aaa@bbb.com"));
	}
	
	@Test
	public void testFindWhenIdDoesExistShouldReturnEntity() {

		EntityManager entityManager = fixture.getEntityManager();
		
		this.setUpNewTransaction(entityManager);
		
		User user = this.getDefaulModelInstanceForInsertion();
		
		fixture.persist(user);
		
		this.commitTransaction(entityManager);
		
		String id = user.getId();
		
		TestCase.assertNotNull(fixture.find(id));
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteWhenObjectIsNullShouldThrowException() {
		this.fixture.delete(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteWhenObjectIsNotManagedShouldThrowException() {
		
		EntityManager entityManager = fixture.getEntityManager();
		
		this.setUpNewTransaction(entityManager);
		
		this.fixture.delete(this.fixture.find(this.getDefaulModelInstanceForInsertion().getId(), entityManager), entityManager);
		
		this.commitTransaction(entityManager);
				
	}
	
	@Test
	public void testDeleteWhenObjectIsManagedShouldDeleteIt() {
		
		EntityManager entityManager = fixture.getEntityManager();
		
		this.setUpNewTransaction(entityManager);
		
		long originalNumberOfUsers = fixture.count(entityManager);
		
		User user = this.getDefaulModelInstanceForInsertion();
		
		fixture.persist(user);
		
		this.commitTransaction(entityManager);
		
		long newNumberOfUsers = fixture.count(entityManager);
		
		TestCase.assertEquals(newNumberOfUsers, originalNumberOfUsers + 1);
		
		String id = user.getId();
		
		TestCase.assertNotNull(id);
		
		this.setUpNewTransaction(entityManager);
		
		fixture.delete(fixture.find(user.getId(), entityManager), entityManager);
		
		this.commitTransaction(entityManager);
		
		TestCase.assertEquals(originalNumberOfUsers, fixture.count(entityManager));
		
		TestCase.assertNull(this.fixture.find(id));
		
	}
	
	@Test
	public void testDeleteGroupWhenObjectIsManagedShouldDeleteGroup() {
		
		EntityManager entityManager = fixture.getEntityManager();
		
		this.setUpNewTransaction(entityManager);
		
		long originalNumberOfUsers = fixture.count(entityManager);
		
		User user = this.getDefaulModelInstanceForInsertion();
		
		fixture.persist(user);
		
		this.commitTransaction(entityManager);
		
		long newNumberOfUsers = fixture.count(entityManager);
		
		TestCase.assertEquals(newNumberOfUsers, originalNumberOfUsers + 1);
		
		TestCase.assertEquals(2, user.getGroups().size());
		
		this.setUpNewTransaction(entityManager);
		
		user = fixture.find(user.getId());
		
		Group group = user.getGroups().iterator().next();
		
		group = fixture.getEntityManager().find(Group.class, group.getId());
		
		TestCase.assertNotNull(group.getId());
		
		user.getGroups().remove(group);
		
		groupDao.delete(groupDao.find(group.getId()));
		
		this.commitTransaction(entityManager);
		
		user = fixture.find(user.getId());
		
		TestCase.assertEquals(1, user.getGroups().size());
		
	}
	
	@Test
	public void testSaveWhenAddingANewGroupAfterSaveShouldWork() {
		
		EntityManager entityManager = fixture.getEntityManager();
		
		this.setUpNewTransaction(entityManager);
		
		long originalNumberOfUsers = fixture.count(entityManager);
		
		User user = this.getDefaulModelInstanceForInsertion();
		
		fixture.persist(user);
		
		this.commitTransaction(entityManager);
		
		long newNumberOfUsers = fixture.count(entityManager);
		
		TestCase.assertEquals(newNumberOfUsers, originalNumberOfUsers + 1);
		
		TestCase.assertEquals(2, user.getGroups().size());
		
		this.setUpNewTransaction(entityManager);
		
		user = fixture.find(user.getId());
		
		Group group = new Group(null, "Test Group", "Test Group used for insertion.", new Date());
		
		user.getGroups().add(group);
		
		this.commitTransaction(entityManager);
		
		user = fixture.find(user.getId());
		
		TestCase.assertEquals(3, user.getGroups().size());
		
	}

	@Override
	protected UserDao getDaoInstance() {
		return fixture;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Class<? extends AbstractEntity<? extends Serializable>>[] getModelClasses() {
		return new Class[] {User.class, Group.class};
	}

	@Override
	protected User getDefaulModelInstanceForInsertion() {
		
		User user = new User();
		
		String userId = "andrei.g.ribas@gmail.com";
		
		user.setId(userId);
		
		user.setEmail(userId);
		
		user.setPassword("myPassword");
		
		Group userGroup = new Group(null, "User Group", "Group used for regular users.", new Date());
		
		Group adminGroup = new Group(null, "Admin Group", "Group used for admins users.", new Date());
		
		Set<Group> userGroups = new HashSet<Group>();
		
		userGroups.add(userGroup);
		
		userGroups.add(adminGroup);
		
		TestCase.assertEquals(2,  userGroups.size());
		
		user.setGroups(userGroups);
		
		user.setCreationDate(new Date());
		
		return user;

	}
	
	@Override
	protected String[] getNativeQueries() {
		return new String[] {
			"delete from tb_user_group"
		};
	}
		
}
