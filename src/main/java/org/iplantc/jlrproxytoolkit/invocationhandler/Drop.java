package org.iplantc.jlrproxytoolkit.invocationhandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class Drop implements InvocationHandler {

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		return null;
	}

}
