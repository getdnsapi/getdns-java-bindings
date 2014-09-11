package com.verisign.getdns;

import java.util.HashMap;

/**
 * The design here is slightly different from the C API in the following ways.
 * 1.  Return the object created or the response.
 * 2.  Throw an exception in case of any issues.
 *
 */
public class GetDNSContext implements IGetDNSContext{
	private Object context;

	static {
		System.loadLibrary("getdnsconnector");
	}
	
	GetDNSContext(int setFromOS) {
		this.context = contextCreate(setFromOS);
	}
	
	/*protected void finalize() throws Throwable {
		contextDestroy(context);
	};*/
	private native Object contextCreate(int setFromOS);
	private native void contextDestroy(Object context);
	
	public void close() {
		contextDestroy(context);
	}

	public HashMap<String,Object> generalSync(String name, RRType requestType, 
			HashMap<String,Object> extensions)throws GetDNSException{
		return generalSync(context, name, requestType.getValue(), extensions);
	}
	
	private native HashMap<String,Object> generalSync(Object context, String name, int requestType, 
			HashMap<String,Object> extensions)throws GetDNSException;
	
	/*public HashMap<String,Object> generalASync(String name, int requestType, 
			HashMap<String,Object> extensions, IGetDNSCallback callback)throws GetDNSException{
		return generalASync(context, name, requestType, extensions, callback);
	}
	
	private native HashMap<String,Object> generalASync(Object context, String name, int requestType, 
			HashMap<String,Object> extensions, Object callbackObj)throws GetDNSException;*/
}
