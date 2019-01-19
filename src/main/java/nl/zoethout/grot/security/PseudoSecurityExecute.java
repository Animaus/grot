package nl.zoethout.grot.security;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Gets annotations from RequestMapping and PseudoSecurity. Uses
 * RequestMapping's 'path' as key to allowed roles given by PseudoSecurity's
 * 'roles'. On runtime gets RequestURI as key. Redirect to homepage on execution
 * denied.
 * 
 * @author Gerard Zoethout
 */
//TODO Check to see re-write as Lambda
public final class PseudoSecurityExecute {
	private PseudoSecurityExecute() {
	}

	public static <T> void run(HttpServletRequest req, HttpServletResponse res, Class<T> clazz) {
		boolean granted = false;
		HttpSession ses = req.getSession();
		String[] roles = (String[]) ses.getAttribute("roles");
		String key = req.getRequestURI();
		Map<String, List<String>> mappings = getMappings(clazz);
		List<String> allowed = mappings.get(key);
		for (String role : roles) {
			if (allowed.contains(role))
				granted = true;
		}
		if (!granted)
			gotoURL(req, res, "/");
	}

	private static <T> Map<String, List<String>> getMappings(Class<T> clazz) {
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		Method[] methods = clazz.getDeclaredMethods();
		RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
		Map<String, List<String>> methodMappings = getMethodMappings(methods);
		if (requestMapping != null) {
			for (String ca : Arrays.asList(requestMapping.path())) {
				for (String ma : methodMappings.keySet()) {
					List<String> allowed = methodMappings.get(ma);
					String key = ca + "/" + ma;
					result.put(key, allowed);
				}
			}
		} else {
			for (String ma : methodMappings.keySet()) {
				List<String> allowed = methodMappings.get(ma);
				String key = ma;
				result.put(key, allowed);
			}
		}
		return result;
	}

	private static Map<String, List<String>> getMethodMappings(Method[] methods) {
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		RequestMapping requestMapping;
		for (Method method : methods) {
			requestMapping = getAnnotation(method, RequestMapping.class);
			if (requestMapping != null) {
				PseudoSecurity pseudoSecurity = getAnnotation(method, PseudoSecurity.class);
				if (pseudoSecurity != null) {
					List<String> allowed = Arrays.asList(pseudoSecurity.roles());
					List<String> paths = Arrays.asList(requestMapping.value());
					for (String p : paths) {
						if (p.indexOf("/") == 0) {
							String key = p.substring(1, p.length());
							result.put(key, allowed);
						} else {
							result.put(p, allowed);
						}
					}
				}
			}
		}
		return result;
	}

	private static <T extends Annotation> T getAnnotation(Method method, Class<T> clazz) {
		return (T) method.getAnnotation(clazz);
	}

	private static void gotoURL(HttpServletRequest req, HttpServletResponse res, String URL) {
		String context = req.getContextPath();
		try {
			res.sendRedirect(context + URL);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
