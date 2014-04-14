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
package com.andreiribas.sistemaaudiencias.cdi.junit;

import java.io.Serializable;

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
public abstract class JpaDaoTest<T extends GenericDao<? extends AbstractEntity<? extends Serializable>, ? extends Serializable>, U extends AbstractEntity<? extends Serializable>> {
	
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
		
	protected EntityTransaction setUpNewTransaction(EntityManager entityManager) {
		
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		if(entityManager.getTransaction().isActive()) {
			throw new org.jboss.weld.exceptions.IllegalStateException("The transaction should not be started already.");
		}
		
		entityTransaction.begin();
	
		return entityTransaction;
		
	}
	
	protected void commitTransaction(EntityManager entityManager) {
		
		entityManager.flush();
		
		entityManager.clear();
		
		entityManager.getTransaction().commit();
	
	}

}
