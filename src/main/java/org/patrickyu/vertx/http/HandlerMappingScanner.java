package org.patrickyu.vertx.http;

import java.util.Set;

import org.reflections.Reflections;

import com.jetdrone.vertx.yoke.middleware.Router;

public class HandlerMappingScanner {
	public static Router scan(String[] packageNames) {
		Router router = new Router();
		for (String packageName: packageNames) {
			doScan(packageName, router);
		}
		return router;
	}

	private static void doScan(String packageName, Router router) {
		Reflections reflections = new Reflections(packageName);
		Set<Class<?>> set = reflections.getTypesAnnotatedWith(RequestHandlerMapping.class);
		for (Class<?> cls: set) {
			RequestHandlerMapping rhm = cls.getAnnotation(RequestHandlerMapping.class);
			if (rhm == null)
				continue;
			BaseRequestHandler handler = null;
			try {
				handler = (BaseRequestHandler)cls.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				continue;
			}
			RequestMethod[] methods = rhm.method();
			for (String path: rhm.value()) {
				router.options(path, new OptionsRequestHandler());
				if (methods.length == 0) {
					router.all(path, handler);
				}
				else {
					for (RequestMethod method: methods) {
						if (method == RequestMethod.DELETE) {
							router.delete(path, handler);
						}
						else if (method == RequestMethod.GET) {
							router.get(path, handler);
						}
						else if (method == RequestMethod.HEAD) {
							router.head(path, handler);
						}
						else if (method == RequestMethod.OPTIONS) {
							router.options(path, handler);
						}
						else if (method == RequestMethod.PATCH) {
							router.patch(path, handler);
						}
						else if (method == RequestMethod.POST) {
							router.post(path, handler);
						}
						else if (method == RequestMethod.PUT) {
							router.put(path, handler);
						}
						else if (method == RequestMethod.TRACE) {
							router.trace(path, handler);
						}
					}
				}
			}
		}
	}
}
