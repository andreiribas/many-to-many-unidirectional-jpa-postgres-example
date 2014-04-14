/**
 * 
 */
package com.andreiribas.sistemaaudiencias.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;

import com.andreiribas.sistemaaudiencias.model.AbstractEntity;

/**
 * @author Andrei Gonçalves Ribas <andrei.g.ribas@gmail.com>
 *
 */
public interface GenericDao<T extends AbstractEntity<I>, I extends Serializable> {

	void persist(T t);
	
	void persist(T t, EntityManager entityManager);

	T merge(T t);
	
	T merge(T t, EntityManager entityManager);
    
	void delete(T t);

	void delete(T t, EntityManager entityManager);
	
    T find(I id);
    
    T find(I id, EntityManager entityManager);
    
    long count();
    
    long count(EntityManager entityManager);
	
	EntityManager getEntityManager();
    
}
