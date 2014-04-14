/**
 * 
 */
package com.andreiribas.sistemaaudiencias.cdi.junit;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.Before;

import com.andreiribas.sistemaaudiencias.dao.GenericDao;
import com.andreiribas.sistemaaudiencias.model.AbstractEntity;

/**
 * @author Andrei Gonçalves Ribas <andrei.g.ribas@gmail.com>
 * 
 */
public abstract class JpaDaoTest<T extends GenericDao<U, ? extends Serializable>, U extends AbstractEntity<? extends Serializable>> {
	
	@Inject
	//private EntityManager entityManager;
	
	//private EntityTransaction transaction;
	
	//private Map<T, EntityManager> entityManagerMap;
	
	/*
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Before
	public void setUp() {
		
		this.entityManagerMap = new HashMap();
		
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		entityTransaction.begin();
		
		for(String nativeQuery: this.getNativeQueries()) {
			entityManager.createNativeQuery(nativeQuery).executeUpdate();
		}
		
		for(Class<? extends AbstractEntity<? extends Serializable>> modelClass: this.getModelClasses()) {
			
			String entityName = modelClass.getSimpleName();
			
			String deleteQuery = String.format("delete from %s", entityName);
			
			entityManager.createQuery(deleteQuery).executeUpdate();
			
		}
		
		entityTransaction.commit();
		
	}
	*/
	
	@Before
	public void setUp() {
		
		EntityManager entityManager = getDaoInstance().getEntityManager();
		
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		entityTransaction.begin();
		
		for(String nativeQuery: this.getNativeQueries()) {
			entityManager.createNativeQuery(nativeQuery).executeUpdate();
		}
		
		for(Class<? extends AbstractEntity<? extends Serializable>> modelClass: this.getModelClasses()) {
			
			String entityName = modelClass.getSimpleName();
			
			String deleteQuery = String.format("delete from %s", entityName);
			
			entityManager.createQuery(deleteQuery).executeUpdate();
			
		}
		
		entityTransaction.commit();
		
	}
	
	@After
	public void tearDown() {
		
		EntityManager entityManager = getDaoInstance().getEntityManager();
		
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		if(entityTransaction.isActive()) {
			entityTransaction.rollback();
		}
	
	}
	
	protected abstract T getDaoInstance();
	
	protected abstract Class<? extends AbstractEntity<? extends Serializable>>[] getModelClasses();
	
	protected String[] getNativeQueries() {
		return new String[] {};
	}
	
	protected abstract U getDefaulModelInstanceForInsertion();
	
	/*
	protected void setUpNewTransaction() {
		
		this.transaction = this.getDaoInstance().getEntityManager().getTransaction();
		
		this.transaction.begin();
	
	}
	*/
	
	protected EntityTransaction setUpNewTransaction(EntityManager entityManager) {
		
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		if(entityManager.getTransaction().isActive()) {
			throw new org.jboss.weld.exceptions.IllegalStateException("The transaction should not be started already.");
		}
		
		entityTransaction.begin();
	
		return entityTransaction;
		
	}
	
	/*
	protected EntityTransaction setUpNewTransaction(T genericDao) {
		
		if(!entityManagerMap.containsKey(genericDao)) {
			
			EntityManager em = genericDao.getEntityManager();
			
			entityManagerMap.put(genericDao, em);
			
		}
		
		EntityTransaction entityTransaction = entityManagerMap.get(genericDao).getTransaction();
		
		entityTransaction.begin();
		
		return entityTransaction;
		
	}
	*/
	
	/*
	protected void commitTransaction() {
		
		entityManager.flush();
		
		entityManager.clear();
		
		this.transaction.commit();
	
	}
	*/
	
	/*
	protected void commitTransaction(T genericDao) {
		
		if(!entityManagerMap.containsKey(genericDao)) {
			throw new IllegalStateException("The developer should have called the setUpNewTransaction(T genericDao) method vefore calling this method.");
		}
		
		EntityManager em = entityManagerMap.get(genericDao);
		
		em.flush();
		
		em.clear();
		
		em.getTransaction().commit();
	
	}
	*/
	
	protected void commitTransaction(EntityManager entityManager) {
		
		entityManager.flush();
		
		entityManager.clear();
		
		entityManager.getTransaction().commit();
	
	}

}
