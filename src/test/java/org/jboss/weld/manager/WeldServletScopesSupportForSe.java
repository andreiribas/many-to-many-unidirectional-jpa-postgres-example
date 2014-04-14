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
import org.jboss.weld.exceptions.DeploymentException;

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
		//BeanManagerImpl beanManagerImpl = (BeanManagerImpl) beanManager;
		BeanManagerProxy beanManagerProxy = (BeanManagerProxy) beanManager;
		
		BeanManagerImpl beanManagerImpl = beanManagerProxy.delegate();
		
		Context context = null;
		
		try {
			
			context = (Context) beanManagerImpl.getContexts().get(cls).get(0);
			
			if(context instanceof BoundRequestContextImpl) {
				
				BoundRequestContextImpl newContext = (BoundRequestContextImpl) context;
				
				Map<String, Object> storage = new HashMap<String, Object>();

				newContext.associate(storage);
				
				newContext.activate();
			
			}
			//if(context instanceof PassivatingContextWrapper.AlterableContextWrapper)
			
			System.out.println(String.format("This class is %s", context.getClass().getName()));
			
			/*
			*/
		} catch(DeploymentException de) {
			
		}
		

	}
	
}