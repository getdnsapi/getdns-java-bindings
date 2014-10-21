package com.verisign.getdns;

import java.util.HashMap;

/**
 * The design here is slightly different from the C API in the following ways.
 * 1.  Return the object created or the response.
 * 2.  Throw an exception in case of any issues.
 *
 */
public class GetDNSContext implements IGetDNSContext{
	protected static Object eventBase = null;
	private Object context;

	static {
		System.loadLibrary("getdnsconnector");
		try {
			initEventingAndListen();
		}catch(RuntimeException exception){
			System.out.println("Error initializing eventing");
		}
	}

	private static void initEventingAndListen() {
		eventBase  = createEventBase();
		if(eventBase !=  null)
			new Thread(){
				public void run() {
					while(true){
						synchronized (eventBase) {
							try {
								eventBase.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
								break;
							}
						}
						startListening(eventBase);
					}
				}
			}.start();
		
		/*
		 * Initial wait, only for now. Should be eliminated.
		 */
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	GetDNSContext(int setFromOS) throws GetDNSException{
		this.context = contextCreate(eventBase, setFromOS);
	}

	/*protected void finalize() throws Throwable {
		contextDestroy(context);
	};*/
	private native Object contextCreate(Object eventBase, int setFromOS);
	private native void contextDestroy(Object context);

	private static native Object createEventBase();

	private static native void startListening(Object eventBase);

	public void close() {
		contextDestroy(context);
	}

	public HashMap<String,Object> generalSync(String name, RRType requestType, 
			HashMap<String,Object> extensions)throws GetDNSException{
		return generalSync(context, name, requestType.getValue(), extensions);
	}

	private native HashMap<String,Object> generalSync(Object context, String name, int requestType, 
			HashMap<String,Object> extensions)throws GetDNSException;

	public long generalASync(String name, RRType requestType, 
			HashMap<String,Object> extensions, IGetDNSCallback callback)throws GetDNSException{
		if(eventBase == null) {
			throw new RuntimeException("Error during eventing init. Cannot invoke async, try sync API");
		}
		long transactionId = generalASync(context, name, requestType.getValue(), extensions, callback);
		synchronized (eventBase) {
			eventBase.notify();
		}
		return transactionId;
	}

	private native long generalASync(Object context, String name, int requestType, 
			HashMap<String,Object> extensions, Object callbackObj)throws GetDNSException;

	private native HashMap<String, Object> addressSync(Object context, String name,
			HashMap<String, Object> extensions) throws GetDNSException;
	
	public  HashMap<String, Object> addressSync(String name,
			HashMap<String, Object> extensions) throws GetDNSException{
		return addressSync(context, name,  extensions);
	}
	
}
