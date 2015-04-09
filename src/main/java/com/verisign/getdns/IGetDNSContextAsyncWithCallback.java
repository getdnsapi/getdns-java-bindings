package com.verisign.getdns;

import java.net.UnknownHostException;
import java.util.HashMap;

public interface IGetDNSContextAsyncWithCallback {
	Long generalAsync(String name, RRType requestType, HashMap<ExtensionName, Object> extensions, IGetDNSCallback callback)
			throws GetDNSException;

	public abstract Long hostnameAsync(String address, HashMap<ExtensionName, Object> extensions, IGetDNSCallback callback)
			throws GetDNSException, UnknownHostException;

	public abstract Long serviceAsync(String name, HashMap<ExtensionName, Object> extensions, IGetDNSCallback callback)
			throws GetDNSException;

	public abstract Long addressAsync(String name, HashMap<ExtensionName, Object> extensions, IGetDNSCallback callback)
			throws GetDNSException;
	
	void cancelRequest(Long transactionId) throws GetDNSException;
}
