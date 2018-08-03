package bookstoread.extensions;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

//TODO p.132 - extensions, test template
public class TypedParameterResolver<T> implements ParameterResolver {
	private T data;

	TypedParameterResolver(T data) {
		this.data = data;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean supportsParameter(ParameterContext parContext, ExtensionContext extContext)
			throws ParameterResolutionException {
		Class clazz = parContext.getParameter().getType();
		return clazz.isInstance(data);
	}

	@Override
	public Object resolveParameter(ParameterContext parContext, ExtensionContext extContext)
			throws ParameterResolutionException {
		return data;
	}
	
}
