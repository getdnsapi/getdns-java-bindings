/*
 * Copyright (c) 2015, Verisign, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * * Neither the names of the copyright holders nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL Verisign, Inc. BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.verisign.getdns;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;

/**
 * <p>
 * The design here is slightly different from the C API in the following ways:
 * <ul>
 * <li>Return the object created or the response</li>
 * <li>Throw an exception in case of any issues</li>
 * </ul>
 *
 */
public class GetDNSContext implements IGetDNSContext, IGetDNSContextWithCallback {
	protected static Object eventBase = null;
	private Object context;
	private ExecutorService executor;

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
		if (extensions != null) {
			HashMap<String, Object> extension = new HashMap<String, Object>();
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
		return generalSync(context, name, requestType.getValue(), getExtension(extensions));
	}

	private native HashMap<String, Object> generalSync(Object context, String name, int requestType,
			HashMap<String, Object> extensions) throws GetDNSException;

	public GetDNSFutureResult generalAsync(String name, RRType requestType, HashMap<ExtensionName, Object> extensions)
			throws GetDNSException {
		if (eventBase == null) {
			throw new RuntimeException("Error during eventing init. Cannot invoke async, try sync API");
		}
		GetDNSFutureResult result = new GetDNSFutureResult(this);
		long transactionId = generalAsync(context, name, requestType.getValue(), getExtension(extensions), result);
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
		return addressSync(context, name, getExtension(extensions));
	}

	private native HashMap<String, Object> serviceSync(Object context, String name, HashMap<String, Object> extensions)
			throws GetDNSException;

	@Override
	public HashMap<String, Object> serviceSync(String name, HashMap<ExtensionName, Object> extensions)
			throws GetDNSException {
		return serviceSync(context, name, getExtension(extensions));

	}

	private native HashMap<String, Object> hostnameSync(Object context, String address, HashMap<String, Object> extensions)
			throws GetDNSException;

	@Override
	public HashMap<String, Object> hostnameSync(String address, HashMap<ExtensionName, Object> extensions)
			throws GetDNSException, UnknownHostException {
		return hostnameSync(context, address, getExtension(extensions));
	}

	@Override
	public GetDNSFutureResult addressAsync(String name, HashMap<ExtensionName, Object> extensions) throws GetDNSException {
		if (eventBase == null) {
			throw new RuntimeException("Error during eventing init. Cannot invoke async, try sync API");
		}
		GetDNSFutureResult result = new GetDNSFutureResult(this);
		long transactionId = addressAsync(context, name, getExtension(extensions), result);
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
		GetDNSFutureResult result = new GetDNSFutureResult(this);
		long transactionId = serviceAsync(context, name, getExtension(extensions), result);
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
		GetDNSFutureResult result = new GetDNSFutureResult(this);
		long transactionId = hostnameAsync(context, address, getExtension(extensions), result);
		result.setTransactionId(transactionId);
		synchronized (eventBase) {
			eventBase.notify();
		}
		return result;
	}

	@Override
	public void setExecutor(ExecutorService executor) {
		this.executor = executor;
	}

	void sendToCallback(final IGetDNSCallback callback, final HashMap<String, Object> response,
			final RuntimeException exception) {
		if (executor != null)
			executor.submit(new Runnable() {
				public void run() {
					if (response != null)
						callback.handleResponse(response, exception);
				}
			});
		else
			callback.handleResponse(response, exception);
	}

	public Long generalAsync(String name, RRType requestType, HashMap<ExtensionName, Object> extensions,
			IGetDNSCallback callback) throws GetDNSException {
		return postProcessingForCallback(generalAsync(name, requestType, extensions), callback);
	}

	@Override
	public Long addressAsync(String name, HashMap<ExtensionName, Object> extensions, IGetDNSCallback callback)
			throws GetDNSException {
		return postProcessingForCallback(addressAsync(name, extensions), callback);
	}

	@Override
	public Long serviceAsync(String name, HashMap<ExtensionName, Object> extensions, IGetDNSCallback callback)
			throws GetDNSException {
		return postProcessingForCallback(serviceAsync(name, extensions), callback);
	}

	@Override
	public Long hostnameAsync(String address, HashMap<ExtensionName, Object> extensions, IGetDNSCallback callback)
			throws GetDNSException, UnknownHostException {
		return postProcessingForCallback(hostnameAsync(address, extensions), callback);
	}

	Long postProcessingForCallback(GetDNSFutureResult result, IGetDNSCallback callback) {
		result.setCallback(callback);
		result.setExecutors(executor);
		return result.getTransactionId();
	}

	public void cancelRequest(Long transactionId) throws GetDNSException {
		cancelRequest(context, transactionId);
	}

	private native void cancelRequest(Object context, long transactionId) throws GetDNSException;

	private native long hostnameAsync(Object context, String address, HashMap<String, Object> extensions,
			Object callbackObj) throws GetDNSException;

	void applyContextOptions(HashMap<ContextOptionName, Object> contextOptions) {
		if (contextOptions == null) {
			return;
		}
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
