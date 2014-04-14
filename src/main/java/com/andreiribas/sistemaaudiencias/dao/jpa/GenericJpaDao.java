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