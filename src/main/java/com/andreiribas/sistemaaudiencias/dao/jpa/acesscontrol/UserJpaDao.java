/**
 * 
 */
package com.andreiribas.sistemaaudiencias.dao.jpa.acesscontrol;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import com.andreiribas.sistemaaudiencias.dao.accesscontrol.UserDao;
import com.andreiribas.sistemaaudiencias.dao.jpa.GenericJpaDao;
import com.andreiribas.sistemaaudiencias.model.accesscontrol.User;

/**
 * @author Andrei Goncalves Ribas <andrei.g.ribas@gmail.com>
 *
 */
@Named
@RequestScoped
public class UserJpaDao extends GenericJpaDao<User, String> implements UserDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2163306125212923115L;
	
	protected UserJpaDao() {}
	
	@Inject
	public UserJpaDao(EntityManager entityManager) {
		super(entityManager, User.class);
	}
	
}
