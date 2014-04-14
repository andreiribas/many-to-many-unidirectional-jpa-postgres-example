/**
 * 
 */
package com.andreiribas.sistemaaudiencias.dao.jpa.acesscontrol;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import com.andreiribas.sistemaaudiencias.dao.accesscontrol.GroupDao;
import com.andreiribas.sistemaaudiencias.dao.jpa.GenericJpaDao;
import com.andreiribas.sistemaaudiencias.model.accesscontrol.Group;

/**
 * @author Andrei Goncalves Ribas <andrei.g.ribas@gmail.com>
 *
 */
@Named
@RequestScoped
public class GroupJpaDao extends GenericJpaDao<Group, Long> implements GroupDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2163306125212923115L;
	
	protected GroupJpaDao() {}
	
	@Inject
	public GroupJpaDao(EntityManager entityManager) {
		super(entityManager, Group.class);
	}
	
}
