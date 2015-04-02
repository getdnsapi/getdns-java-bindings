package com.verisign.getdns;

import java.net.UnknownHostException;
import java.util.HashMap;

/**
 * <p>
 * The design here is slightly different from the C API in the following ways:
 * <ul>
 * <li>Return the object created or the response</li>
 * <li>Throw an exception in case of any issues</li>
 * </ul>
 *
 */
public class GetDNSContext implements IGetDNSContext {
	protected static Object eventBase = null;
	private Object context;

	static {
		System.loadLibrary("getdnsconnector");
		try {
			initEventingAndListen();
		} catch (RuntimeException exception) {
			System.out.println("Error initializing eventing");
		}
	}

	private static void initEventingAndListen() {
		eventBase = createEventBase();
		if (eventBase != null)
			new Thread() {
				public void run() {
					while (true) {
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

	GetDNSContext(int setFromOS) throws GetDNSException {
		this.context = contextCreate(eventBase, setFromOS);
	}

	/*
	 * protected void finalize() throws Throwable { contextDestroy(context); };
	 */
	private native Object contextCreate(Object eventBase, int setFromOS);

	private native void contextDestroy(Object context);

	private static native Object createEventBase();

	private static native void startListening(Object eventBase);

	public void close() {
		contextDestroy(context);
	}

	private HashMap<String, Object> getExtension(HashMap<ExtensionName, Object> extensions) {
		HashMap<String, Object> extension = new HashMap<String, Object>();
		if (extensions != null) {
			for (ExtensionName extensionName : extensions.keySet()) {
				extension.put(extensionName.getName(), extensions.get(extensionName));
			}
			return extension;
		} else {
			return null;
		}
	}

	public HashMap<String, Object> generalSync(String name, RRType requestType, HashMap<ExtensionName, Object> extensions)
			throws GetDNSException {
		HashMap<String, Object> extension = new HashMap<String, Object>();
		extension = getExtension(extensions);
		return generalSync(context, name, requestType.getValue(), extension);
	}

	private native HashMap<String, Object> generalSync(Object context, String name, int requestType,
			HashMap<String, Object> extensions) throws GetDNSException;

	public GetDNSFutureResult generalAsync(String name, RRType requestType, HashMap<ExtensionName, Object> extensions)
			throws GetDNSException {
		if (eventBase == null) {
			throw new RuntimeException("Error during eventing init. Cannot invoke async, try sync API");
		}
		HashMap<String, Object> extension = new HashMap<String, Object>();
		extension = getExtension(extensions);
		GetDNSFutureResult result = new GetDNSFutureResult(context);
		long transactionId = generalAsync(context, name, requestType.getValue(), extension, result);
		result.setTransactionId(transactionId);
		synchronized (eventBase) {
			eventBase.notify();
		}
		return result;
	}

	private native long generalAsync(Object context, String name, int requestType, HashMap<String, Object> extensions,
			Object callbackObj) throws GetDNSException;

	private native HashMap<String, Object> addressSync(Object context, String name, HashMap<String, Object> extensions)
			throws GetDNSException;

	public HashMap<String, Object> addressSync(String name, HashMap<ExtensionName, Object> extensions)
			throws GetDNSException {
		HashMap<String, Object> extension = new HashMap<String, Object>();
		extension = getExtension(extensions);
		return addressSync(context, name, extension);
	}

	private native HashMap<String, Object> serviceSync(Object context, String name, HashMap<String, Object> extensions)
			throws GetDNSException;

	@Override
	public HashMap<String, Object> serviceSync(String name, HashMap<ExtensionName, Object> extensions)
			throws GetDNSException {
		HashMap<String, Object> extension = new HashMap<String, Object>();
		extension = getExtension(extensions);
		return serviceSync(context, name, extension);

	}

	private native HashMap<String, Object> hostnameSync(Object context, String address, HashMap<String, Object> extensions)
			throws GetDNSException;

	@Override
	public HashMap<String, Object> hostnameSync(String address, HashMap<ExtensionName, Object> extensions)
			throws GetDNSException, UnknownHostException {
		HashMap<String, Object> extension = new HashMap<String, Object>();
		extension = getExtension(extensions);
		return hostnameSync(context, address, extension);
	}

	@Override
	public GetDNSFutureResult addressAsync(String name, HashMap<ExtensionName, Object> extensions) throws GetDNSException {
		if (eventBase == null) {
			throw new RuntimeException("Error during eventing init. Cannot invoke async, try sync API");
		}
		GetDNSFutureResult result = new GetDNSFutureResult(context);
		HashMap<String, Object> extension = new HashMap<String, Object>();
		extension = getExtension(extensions);
		long transactionId = addressAsync(context, name, extension, result);
		result.setTransactionId(transactionId);
		synchronized (eventBase) {
			eventBase.notify();
		}
		return result;
	}

	private native long addressAsync(Object context, String name, HashMap<String, Object> extensions, Object callbackObj)
			throws GetDNSException;

	@Override
	public GetDNSFutureResult serviceAsync(String name, HashMap<ExtensionName, Object> extensions) throws GetDNSException {
		if (eventBase == null) {
			throw new RuntimeException("Error during eventing init. Cannot invoke async, try sync API");
		}
		HashMap<String, Object> extension = new HashMap<String, Object>();
		extension = getExtension(extensions);
		GetDNSFutureResult result = new GetDNSFutureResult(context);
		long transactionId = serviceAsync(context, name, extension, result);
		result.setTransactionId(transactionId);
		synchronized (eventBase) {
			eventBase.notify();
		}
		return result;
	}

	private native long serviceAsync(Object context, String name, HashMap<String, Object> extensions, Object callbackObj)
			throws GetDNSException;

	@Override
	public GetDNSFutureResult hostnameAsync(String address, HashMap<ExtensionName, Object> extensions)
			throws GetDNSException, UnknownHostException {
		if (eventBase == null) {
			throw new RuntimeException("Error during eventing init. Cannot invoke async, try sync API");
		}
		GetDNSFutureResult result = new GetDNSFutureResult(context);
		HashMap<String, Object> extension = new HashMap<String, Object>();
		extension = getExtension(extensions);
		long transactionId = hostnameAsync(context, address, extension, result);
		result.setTransactionId(transactionId);
		synchronized (eventBase) {
			eventBase.notify();
		}
		return result;
	}

	private native long hostnameAsync(Object context, String address, HashMap<String, Object> extensions,
			Object callbackObj) throws GetDNSException;

	void applyContextOptions(HashMap<ContextOptionName, Object> contextOptions) {
		for (ContextOptionName optionName : contextOptions.keySet()) {
			Object val = convertContextOptionValuesIfneeded(contextOptions.get(optionName));
			applyContextOption(context, optionName.getName(), val);
		}
	}

	Object convertContextOptionValuesIfneeded(Object targetValue) {
		Object val = targetValue;
		if (targetValue instanceof ContextOptionValue) {
			val = ((ContextOptionValue) targetValue).getvalue();
		} else if (targetValue instanceof Object[]) {
			Object[] value = ((Object[]) targetValue).clone(); // Avoiding modifying
																													// user provided
																													// object
			for (int i = 0; i < value.length && (value[i] instanceof ContextOptionValue); i++) {
				value[i] = ((ContextOptionValue) value[i]).getvalue();
			}
			val = value;
		}
		return val;
	}

	/**
	 * 
	 * @param context
	 * @param optionName
	 * @param value
	 */
	private native void applyContextOption(Object context, String optionName, Object value);

	/**
	 * 
	 * This methods converts Unicode to Ascii
	 * 
	 * @param unicode
	 * @return
	 * @throws GetDNSException
	 */
	public static native String ConvertUnicodeToAscii(String unicode) throws GetDNSException;

	/**
	 * This methods converts Ascii to Unicode
	 * 
	 * @param ascii
	 * @return
	 * @throws GetDNSException
	 */
	public static native String ConvertAsciiToUnicode(String ascii) throws GetDNSException;

	/**
	 * Default list of trust anchor records that is used by the library to
	 * validate DNSSEC can be retrieved using this method
	 * 
	 * @return
	 * @throws GetDNSException
	 */
	public static native Object[] GetDnsRootTrustAnchor() throws GetDNSException;

}
