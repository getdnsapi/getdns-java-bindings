package com.verisign.getdns;

import java.net.UnknownHostException;
import java.util.HashMap;

public interface IGetDNSContext {

	HashMap<String, Object> generalSync(String name, RRType requestType, HashMap<String, Object> extensions)
			throws GetDNSException;

	HashMap<String, Object> addressSync(String name, HashMap<String, Object> extensions) throws GetDNSException;

	HashMap<String, Object> serviceSync(String name, HashMap<String, Object> extensions) throws GetDNSException;

	HashMap<String, Object> hostnameSync(String address, HashMap<String, Object> extensions) throws GetDNSException,
			UnknownHostException;

	GetDNSFutureResult generalAsync(String name, RRType requestType, HashMap<String, Object> extensions)
			throws GetDNSException;

	GetDNSFutureResult addressAsync(String name, HashMap<String, Object> extensions) throws GetDNSException;

	GetDNSFutureResult serviceAsync(String name, HashMap<String, Object> extensions) throws GetDNSException;

	GetDNSFutureResult hostnameAsync(String address, HashMap<String, Object> extensions) throws GetDNSException,
			UnknownHostException;

	void close();

}
