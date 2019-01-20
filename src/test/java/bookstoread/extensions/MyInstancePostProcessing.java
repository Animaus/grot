package bookstoread.extensions;

import java.lang.reflect.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

// TODO p.126-7 - extensions, test instance post-processing
public class MyInstancePostProcessing implements TestInstancePostProcessor {

	@Override
	public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
		Logger logger = LoggerFactory.getLogger(testInstance.getClass());
		Field field = testInstance.getClass().getDeclaredField("logger");
		field.set(testInstance, logger);
	}

}
