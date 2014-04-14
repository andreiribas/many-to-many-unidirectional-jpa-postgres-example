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
package org.jboss.weld.manager; // required for visibility to BeanManagerImpl#getContexts()

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.context.spi.Context;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;

import org.jboss.weld.bean.builtin.BeanManagerProxy;
import org.jboss.weld.context.bound.BoundRequestContextImpl;

/**
 * @author Andrei Goncalves Ribas <andrei.g.ribas@gmail.com>
 *
 */
public class WeldServletScopesSupportForSe implements Extension {

	public void afterDeployment(@Observes AfterDeploymentValidation event,
			BeanManager beanManager) {

		setContextActive(beanManager, RequestScoped.class);
		
		setContextActive(beanManager, SessionScoped.class);
		
	}

	private void setContextActive(BeanManager beanManager,
			Class<? extends Annotation> cls) {
		
		BeanManagerProxy beanManagerProxy = (BeanManagerProxy) beanManager;
		
		BeanManagerImpl beanManagerImpl = beanManagerProxy.delegate();
		
		Context context = (Context) beanManagerImpl.getContexts().get(cls).get(0);
		
		if(context instanceof BoundRequestContextImpl) {
			
			BoundRequestContextImpl newContext = (BoundRequestContextImpl) context;
			
			Map<String, Object> storage = new HashMap<String, Object>();
	
			newContext.associate(storage);
			
			newContext.activate();
		
		}
			
	}
	
}