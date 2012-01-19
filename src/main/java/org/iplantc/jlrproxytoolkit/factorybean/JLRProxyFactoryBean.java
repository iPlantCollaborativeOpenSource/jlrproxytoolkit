package org.iplantc.jlrproxytoolkit.factorybean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import org.springframework.beans.factory.FactoryBean;

public class JLRProxyFactoryBean implements FactoryBean {

	private Class<?>[] interfaces;
	private InvocationHandler invocationHandler;
	
	public Object getObject() throws Exception {
		return Proxy.newProxyInstance(this.getClass().getClassLoader(), interfaces, invocationHandler);
	}

	public Class<?> getObjectType() {
		if (interfaces == null) {
			return null;
		}
		return Proxy.getProxyClass(this.getClass().getClassLoader(), interfaces);
	}

	public boolean isSingleton() {
		return false;
	}

	public Class<?>[] getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(Class<?>[] interfaces) {
		this.interfaces = interfaces;
	}

	public InvocationHandler getInvocationHandler() {
		return invocationHandler;
	}

	public void setInvocationHandler(InvocationHandler invocationHandler) {
		this.invocationHandler = invocationHandler;
	}

}
