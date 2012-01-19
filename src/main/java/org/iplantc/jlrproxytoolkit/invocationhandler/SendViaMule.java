package org.iplantc.jlrproxytoolkit.invocationhandler;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.transport.NullPayload;

public class SendViaMule implements InvocationHandler {

	private boolean synchronous;
	private String endpoint;
	private Class<? extends Throwable> exceptionClass;
	
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object objectToSend;
		Object ret = null;
		
		if (args.length == 1) {
			objectToSend = args[0];
		} else {
			objectToSend = args;
		}
		
		MuleClient client = new MuleClient(false);
		
		if (synchronous) {
			MuleMessage retMsg;
			try {
				retMsg = client.send(endpoint, objectToSend, null);
			} catch (MuleException me) {
				Constructor<? extends Throwable> c = exceptionClass.getConstructor(String.class, Throwable.class);
				throw c.newInstance(me.getMessage(), me);
			}
			ret = retMsg.getPayload();
			if (ret instanceof NullPayload) {
				ret = null;
			}
			// This might be needed later, but it introduces a dependency on muletoolkit,
			// so leave out for now
			/* else if (ret instanceof ErrorPayload) {
				Constructor<? extends Throwable> c = exceptionClass.getConstructor(String.class);
				Throwable e = c.newInstance(((ErrorPayload)ret).getErrorMessage());
				throw e;
			}*/
		} else {
			client.dispatch(endpoint, objectToSend, null);
		}

		return ret;
	}

	public boolean isSynchronous() {
		return synchronous;
	}

	public void setSynchronous(boolean synchronous) {
		this.synchronous = synchronous;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public void setExceptionClass(Class<? extends Throwable> exceptionClass) {
		this.exceptionClass = exceptionClass;
	}

	public Class<? extends Throwable> getExceptionClass() {
		return exceptionClass;
	}

}
