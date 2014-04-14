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
package com.andreiribas.sistemaaudiencias.dao.jpa;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.andreiribas.sistemaaudiencias.dao.GenericDao;
import com.andreiribas.sistemaaudiencias.model.AbstractEntity;

/**
 * @author Andrei Goncalves Ribas <andrei.g.ribas@gmail.com>
 *
 */
@RequestScoped
public abstract class GenericJpaDao<T extends AbstractEntity<I>, I extends Serializable> implements GenericDao<T, I>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2973854079424656407L;

	private final EntityManager entityManager;
	
	private final Class<T> entityClass;
	
	protected GenericJpaDao() {
		this(null, null);
	}
	
	@Inject
	public GenericJpaDao(EntityManager entityManager, Class<T> entityClass) {
		
		this.entityManager = entityManager;
		
		this.entityClass = entityClass;
		
	}
	
	@Override
	public void persist(final T t) {
		this.persist(t, this.entityManager);
	}
	
	@Override
	public void persist(T t, EntityManager entityManager) {
		entityManager.persist(t);
	}
	
	@Override
	public T merge(final T t) {
		return this.merge(t, this.entityManager);
	}
	
	@Override
	public T merge(final T t, EntityManager entityManager) {
		return entityManager.merge(t);
	}

	@Override
	public void delete(T t) {
		this.delete(t, this.entityManager);
	}
	
	@Override
	public void delete(T t, EntityManager entityManager) {
		entityManager.remove(t);
	}
	
	@Override
	public T find(I id) {
		return this.find(id, entityManager);
	}
	
	@Override
	public T find(I id, EntityManager entityManager) {
		return entityManager.find(entityClass, id);
	}
	
	@Override
	public long count() {
		return this.count(this.entityManager);
	}
	
	@Override
	public long count(EntityManager entityManager) {
		
		Query query = entityManager.createQuery(String.format("SELECT COUNT(%s) FROM %s %s", this.entityClass.getSimpleName(), this.entityClass.getSimpleName(), this.entityClass.getSimpleName()));
				
		return ((Long) query.getSingleResult()).longValue();
		
	}

	@Override
	public EntityManager getEntityManager() {
		return this.entityManager;
	}
	
}