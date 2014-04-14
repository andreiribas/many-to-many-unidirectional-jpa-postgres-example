/**
 * 
 */
package com.andreiribas.sistemaaudiencias.model.accesscontrol;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.andreiribas.sistemaaudiencias.model.AbstractEntity;

/**
 * @author Andrei Goncalves Ribas <andrei.g.ribas@gmail.com>
 *
 */
@Entity
@Table(name = "tb_user")
public class User extends AbstractEntity<String> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8010297649753105077L;

	private String password;
	
	private String email;
	
	private Set<Group> groups;
	
	private Date creationDate;
	
	@Id
	@Override
	public String getId() {
		return super.getId();
	}
	
	@Override
	public void setId(String id) {
		super.setId(id);
	}
	
	@Column(length = 255, nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(length = 255, nullable = false, unique = true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
		
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "tb_user_group", 
    			joinColumns = @JoinColumn(name = "user_id"), 
    			inverseJoinColumns = @JoinColumn(name = "group_id"))
    public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}
