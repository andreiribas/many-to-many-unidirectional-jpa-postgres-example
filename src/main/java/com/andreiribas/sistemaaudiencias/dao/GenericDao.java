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
