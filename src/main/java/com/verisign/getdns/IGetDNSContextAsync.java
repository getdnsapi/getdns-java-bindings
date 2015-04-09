package com.verisign.getdns;

import java.net.UnknownHostException;
import java.util.HashMap;

interface IGetDNSContextAsync {
	/**
	 * 
	 * @param name
	 * @param requestType
	 * @param extensions
	 * @return
	 * @throws GetDNSException
	 */
	GetDNSFutureResult generalAsync(String name, RRType requestType, HashMap<ExtensionName, Object> extensions)

	throws GetDNSException;
	
	/**
	 * 
	 * @param name
	 * @param extensions
	 * @return
	 * @throws GetDNSException
	 */
	GetDNSFutureResult addressAsync(String name, HashMap<ExtensionName, Object> extensions) throws GetDNSException;

	/**
	 * 
	 * @param name
	 * @param extensions
	 * @return
	 * @throws GetDNSException
	 */
	GetDNSFutureResult serviceAsync(String name, HashMap<ExtensionName, Object> extensions) throws GetDNSException;

	/**
	 * 
	 * @param address
	 * @param extensions
	 * @return
	 * @throws GetDNSException
	 * @throws UnknownHostException
	 */
	GetDNSFutureResult hostnameAsync(String address, HashMap<ExtensionName, Object> extensions) throws GetDNSException,
			UnknownHostException;

}
