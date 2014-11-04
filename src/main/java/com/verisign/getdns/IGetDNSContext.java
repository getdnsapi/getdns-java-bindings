package com.verisign.getdns;

import java.util.HashMap;

public interface IGetDNSContext {

	HashMap<String, Object> generalSync(String name, RRType requestType,
			HashMap<String, Object> extensions) throws GetDNSException;

	HashMap<String, Object> addressSync(String name,
			HashMap<String, Object> extensions) throws GetDNSException;

	void close();

	HashMap<String, Object> serviceSync(String name,
			HashMap<String, Object> extensions) throws GetDNSException;

	HashMap<String, Object> hostnameSync(HashMap<String, Object> address, HashMap<String, Object> extensions)
			throws GetDNSException;

	GetDNSFutureResult generalASync(String name, RRType requestType,
			HashMap<String, Object> extensions)
			throws GetDNSException;

}
