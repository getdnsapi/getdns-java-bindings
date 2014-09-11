package com.verisign.getdns;

import java.util.HashMap;

public interface IGetDNSContext {

	HashMap<String,Object> generalSync(String name, RRType requestType, HashMap<String,Object> extensions)
			throws GetDNSException;

	void close();

	/*HashMap<String,Object> generalASync(String name, int requestType, HashMap<String,Object> extensions,
			IGetDNSCallback callback) throws GetDNSException;*/

}
