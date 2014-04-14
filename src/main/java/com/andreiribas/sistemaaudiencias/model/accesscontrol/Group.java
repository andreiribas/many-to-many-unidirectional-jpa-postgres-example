/**
 * 
 */
package com.andreiribas.sistemaaudiencias.model.accesscontrol;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.andreiribas.sistemaaudiencias.model.AbstractEntity;

/**
 * @author Andrei Goncalves Ribas <andrei.g.ribas@gmail.com>
 *
 */
@Entity
@Table(name = "tb_group")
@SequenceGenerator(name = "seq_group", allocationSize = 1, sequenceName = "seq_group")
public class Group extends AbstractEntity<Long> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6198673577125091039L;

	private String name;
	
	private String description;
	
	private Date creationDate;
	
	public Group() {}
	
	public Group(Long id) {
		super(id);
	}
	
	public Group(Long id, String name, String description, Date creationDate) {
		this(id);
		this.name = name;
		this.description = description;
		this.creationDate = creationDate;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_group")
	@Override
	public Long getId() {
		return super.getId();
	}
	
	@Override
	public void setId(Long id) {
		super.setId(id);
	}

	@Column(length = 255, name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(length = 1023, name = "description", nullable = true)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
