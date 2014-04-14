/**
 * 
 */
package com.andreiribas.sistemaaudiencias.util.cdi;

import java.io.Serializable;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

/**
 * @author Andrei Goncalves Ribas <andrei.g.ribas@gmail.com>
 *
 */
public class EntityManagerProducer implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6934038219040491371L;

	private static final Logger LOGGER = Logger.getLogger(EntityManagerProducer.class);
	
	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("MANY_TO_MANY_UNIDIRECTIONAL_EXAMPLE");
	
	@Produces
	@Singleton
	public EntityManager createEntityManager() {
		try {
			return EntityManagerProducer.entityManagerFactory.createEntityManager();
		} catch(RuntimeException re) {
		
			LOGGER.fatal("Error while creating EntityManager from EntityManagerFactory.", re);
		
			throw re;
		
		}
		
	}
	
}
