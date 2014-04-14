/**
 * 
 */
package com.andreiribas.sistemaaudiencias.model;

import java.io.Serializable;

/**
 * @author Andrei Goncalves Ribas <andrei.g.ribas@gmail.com>
 *
 */
public abstract class AbstractEntity<IdType extends Serializable> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5164446735795656651L;
	
	private IdType id;
	
	public AbstractEntity() {
		super();
	}

	public AbstractEntity(IdType id) {
		
		this();
		
		this.id = id;
	
	}

	public IdType getId() {
		return id;
	}

	public void setId(IdType id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return String.format("[Class: %s, id: %s]", this.getClass().getSimpleName(), this.id);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
		
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractEntity<IdType> other = (AbstractEntity<IdType>) obj;
		if (id == null) {
			return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
