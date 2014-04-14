/**
 * 
 */
package com.andreiribas.sistemaaudiencias.cdi.junit;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.TestClass;

/**
 * @author Andrei Gonçalves Ribas <andrei.g.ribas@gmail.com>
 * 
 */
public class WeldJUnit4Runner extends BlockJUnit4ClassRunner {

	private final Weld weld;

	private final WeldContainer container;

	public WeldJUnit4Runner(final Class<?> testClass)
			throws InitializationError {

		super(testClass);
		
		long startCdiTime = System.nanoTime();

		this.weld = new Weld();

		this.container = weld.initialize();

		long endCdiTime = System.nanoTime();

		System.out.println(String.format("Weld setup in %d miliseconds.",
				TimeUnit.NANOSECONDS.toMillis(endCdiTime - startCdiTime)));

	}

	@Override
	protected Object createTest() throws Exception {

		TestClass testClassJunitModel = getTestClass();

		Class<?> testClass = testClassJunitModel.getJavaClass();

		Object testInstance = testClass.newInstance();
		
		setFieldsFromClass(testInstance, testClass);

		return testInstance;

	}
	
	private void setFieldsFromClass(Object instance, Class<?> clazz) throws IllegalArgumentException, IllegalAccessException {
		
		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {

			field.setAccessible(true);

			Inject injectAnnotation = field.getAnnotation(Inject.class);

			if (injectAnnotation != null) {

				Class<?> fieldType = field.getType();

				long beanResolutionStartTime = System.nanoTime();

				Object fieldValueInjectedFromCdi = container.instance()
						.select(fieldType).get();

				long beanResolutionEndTime = System.nanoTime();

				System.out.println(String.format(
						"Bean resolution of class %s in %d miliseconds.",
						fieldType.getSimpleName(),
						TimeUnit.NANOSECONDS.toMillis(beanResolutionEndTime
								- beanResolutionStartTime)));

				field.set(instance, fieldValueInjectedFromCdi);

			}

		}
		
		if(clazz.getSuperclass() != null) {
			setFieldsFromClass(instance, clazz.getSuperclass());
		}
		
	}

}