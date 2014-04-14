/**
 * 
 */
package com.andreiribas.sistemaaudiencias.dao.jpa.accesscontrol;


import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.andreiribas.sistemaaudiencias.cdi.junit.JpaDaoTest;
import com.andreiribas.sistemaaudiencias.cdi.junit.WeldJUnit4Runner;
import com.andreiribas.sistemaaudiencias.dao.accesscontrol.GroupDao;
import com.andreiribas.sistemaaudiencias.model.AbstractEntity;
import com.andreiribas.sistemaaudiencias.model.accesscontrol.Group;

/**
 * @author Andrei Gonçalves Ribas <andrei.g.ribas@gmail.com>
 *
 */
@RunWith(WeldJUnit4Runner.class)
public class GroupDaoTest extends JpaDaoTest<GroupDao, Group> {
	
	@Inject
	private GroupDao fixture;
	
	@Test
	public void testSaveWhenAllDataIsOkShouldSucceed() {
		
		EntityManager entityManager = fixture.getEntityManager();
		
		Group group = this.getDefaulModelInstanceForInsertion();
		
		this.setUpNewTransaction(entityManager);
		
		fixture.persist(group, entityManager);
		
		this.commitTransaction(entityManager);
		
		TestCase.assertNotNull(group);
		
		TestCase.assertNotNull(group.getId());
		
	}
	
	@Test(expected = PersistenceException.class)
	public void testSaveWhenNameIsNullShouldThrowException() {
		
		EntityManager entityManager = fixture.getEntityManager();
		
		Group group = this.getDefaulModelInstanceForInsertion();
		
		group.setName(null);
		
		this.setUpNewTransaction(entityManager);
		
		fixture.persist(group, entityManager);
		
		this.commitTransaction(entityManager);
		
		TestCase.assertNotNull(group);
		
		TestCase.assertNotNull(group.getId());
		
	}
	
	@Test
	public void testSaveWhenNameIsEmptyShouldNotThrowException() {
		
		EntityManager entityManager = fixture.getEntityManager();
		
		Group group = this.getDefaulModelInstanceForInsertion();
		
		group.setName("");
		
		this.setUpNewTransaction(entityManager);
		
		fixture.persist(group, entityManager);
		
		this.commitTransaction(entityManager);
		
		TestCase.assertNotNull(group);
		
		TestCase.assertNotNull(group.getId());
		
	}
	
	@Test(expected = PersistenceException.class)
	public void testSaveWhenCreationDateIsEmptyShouldThrowException() {
		
		EntityManager entityManager = fixture.getEntityManager();
		
		Group group = this.getDefaulModelInstanceForInsertion();
		
		group.setCreationDate(null);
		
		this.setUpNewTransaction(entityManager);
		
		fixture.persist(group, entityManager);
		
		this.commitTransaction(entityManager);
		
		TestCase.assertNotNull(group);
		
		TestCase.assertNotNull(group.getId());
		
	}
	
	@Test
	public void testSaveAndUpdateItsNameWhenItsManaged() {
		
		EntityManager entityManager = fixture.getEntityManager();
		
		this.setUpNewTransaction(entityManager);
		
		long numberOfGroups = fixture.count(entityManager);
			
		TestCase.assertNotSame(-1L, numberOfGroups);
		
		Group group = this.getDefaulModelInstanceForInsertion();
				
		fixture.persist(group, entityManager);
		
		//new user is managed by jpa now.
		
		this.commitTransaction(entityManager);
		
		this.setUpNewTransaction(entityManager);
		
		Long id = group.getId();
		
		TestCase.assertNotNull(id);
		
		group = fixture.find(id, entityManager);
		
		String newName = "New Test Name";
		
		//group is updated without using merge, because it's already managed
		group.setName(newName);
		
		TestCase.assertNotNull(group);
		
		TestCase.assertEquals(newName, group.getName());
		
		this.commitTransaction(entityManager);
		
		//this.setUpNewTransaction(entityManager);
		
		TestCase.assertEquals(numberOfGroups + 1, fixture.count(entityManager));
		
		//this.commitTransaction(entityManager);
		
	}
	
	@Test
	public void testSaveAndUpdateItsNameWithMerge() {
		
		EntityManager entityManager = fixture.getEntityManager();
		
		this.setUpNewTransaction(entityManager);
		
		long numberOfUsers = fixture.count(entityManager);
			
		TestCase.assertNotSame(-1L, numberOfUsers);
		
		Group group = this.getDefaulModelInstanceForInsertion();
		
		fixture.persist(group, entityManager);
		
		this.commitTransaction(entityManager);
		
		this.setUpNewTransaction(entityManager);
		
		Long id = group.getId();
		
		TestCase.assertNotNull(id);
		
		Group groupAtualizado = fixture.find(id, entityManager);
		
		String newName = "New Group Name for Test.";
		
		groupAtualizado.setName(newName);
			
		//newUser is updated using merge, because it's already managed
		
		groupAtualizado = fixture.merge(groupAtualizado, entityManager);
		
		TestCase.assertNotNull(groupAtualizado);
		
		TestCase.assertEquals(newName, groupAtualizado.getName());
		
		this.commitTransaction(entityManager);
		
		//this.setUpNewTransaction(entityManager);
		
		groupAtualizado = fixture.find(id, entityManager);
		
		TestCase.assertEquals(newName, groupAtualizado.getName());
		
		TestCase.assertEquals(numberOfUsers + 1, fixture.count(entityManager));
		
		//this.commitTransaction(entityManager);
		
	}
	
	@Test
	public void testCreateNewUserAndDeleteIt() {
		
		EntityManager entityManager = fixture.getEntityManager();
		
		this.setUpNewTransaction(entityManager);
		
		long numberOfUsers = fixture.count(entityManager);
		
		Group group = this.getDefaulModelInstanceForInsertion();
		
		fixture.persist(group, entityManager);
		
		this.commitTransaction(entityManager);
		
		Long id = group.getId();
		
		TestCase.assertNotNull(id);
		
		this.setUpNewTransaction(entityManager);
		
		Group groupAtualizado = fixture.find(id, entityManager);
		
		fixture.delete(groupAtualizado);
		
		this.commitTransaction(entityManager);
		
		this.setUpNewTransaction(entityManager);
		
		TestCase.assertEquals(numberOfUsers, fixture.count(entityManager));
		
		this.commitTransaction(entityManager);
		
	}
	
	@Test
	public void testCountWhenThereAreNoGroupsShouldReturnZero() {
		TestCase.assertEquals(0, fixture.count());
	}
	
	@Test
	public void testCountWhenThereIsOnlyOneGroupShouldReturnOne() {

		EntityManager entityManager = fixture.getEntityManager();
		
		this.setUpNewTransaction(entityManager);
		
		Group group = this.getDefaulModelInstanceForInsertion();
		
		fixture.persist(group, entityManager);
		
		this.commitTransaction(entityManager);
		
		TestCase.assertEquals(1, fixture.count(entityManager));
	
	}
	
	@Test
	public void testCountWhenThereAreMoreThanOneGroupsShouldReturnNumberGreaterThanZero() {

		EntityManager entityManager = fixture.getEntityManager();
		
		this.setUpNewTransaction(entityManager);
		
		Group group = this.getDefaulModelInstanceForInsertion();
		
		fixture.persist(group, entityManager);
		
		Group novoGroup = this.getDefaulModelInstanceForInsertion();
		
		fixture.persist(novoGroup);
		
		this.commitTransaction(entityManager);
		
		TestCase.assertTrue(fixture.count(entityManager) > 1);
	
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testFindWhenIdIsNullShouldThrowException() {
		fixture.find(null);
	}
	
	@Test
	public void testFindWhenIdDoesNotExistShouldReturnNull() {
		TestCase.assertNull(fixture.find(Long.MAX_VALUE));
	}
	
	@Test
	public void testFindWhenIdDoesExistShouldReturnEntity() {

		EntityManager entityManager = fixture.getEntityManager();
		
		this.setUpNewTransaction(entityManager);
		
		Group group = this.getDefaulModelInstanceForInsertion();
		
		fixture.persist(group, entityManager);
		
		this.commitTransaction(entityManager);
		
		Long id = group.getId();
		
		TestCase.assertNotNull(fixture.find(id));
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteWhenObjectIsNullShouldThrowException() {
		this.fixture.delete(null);
	}
	
	@Test
	public void testDeleteWhenObjectIsNotManagedShouldDoNothing() {
		this.fixture.delete(this.getDefaulModelInstanceForInsertion());
	}
	
	@Test
	public void testDeleteWhenObjectIsManagedShouldDeleteIt() {
		
		EntityManager entityManager = fixture.getEntityManager();
		
		this.setUpNewTransaction(entityManager);
		
		long originalNumberOfUsers = fixture.count(entityManager);
		
		Group group = this.getDefaulModelInstanceForInsertion();
		
		fixture.persist(group, entityManager);
		
		this.commitTransaction(entityManager);
		
		long newNumberOfUsers = fixture.count(entityManager);
		
		TestCase.assertEquals(newNumberOfUsers, originalNumberOfUsers + 1);
		
		Long id = group.getId();
		
		TestCase.assertNotNull(id);
		
		this.setUpNewTransaction(entityManager);
		
		fixture.delete(fixture.find(group.getId(), entityManager), entityManager);
		
		this.commitTransaction(entityManager);
		
		TestCase.assertEquals(originalNumberOfUsers, fixture.count(entityManager));
		
		TestCase.assertNull(this.fixture.find(id));
		
	}

	@Override
	protected GroupDao getDaoInstance() {
		return fixture;
	}

	@Override
	protected Group getDefaulModelInstanceForInsertion() {
		
		Group group = new Group();
		
		group.setName("Test Group");
		
		group.setDescription("Test Description");
		
		group.setCreationDate(new Date());
		
		return group;

	}

	@SuppressWarnings("unchecked")
	@Override
	protected Class<? extends AbstractEntity<? extends Serializable>>[] getModelClasses() {
		return new Class[] { Group.class };
	}
	
	@Override
	protected String[] getNativeQueries() {
		return new String[] {
			"delete from tb_user_group"
		};
	}
	
}
