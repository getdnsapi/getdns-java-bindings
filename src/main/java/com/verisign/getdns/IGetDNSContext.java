package com.verisign.getdns;

import java.util.HashMap;

public interface IGetDNSContext {

	HashMap<String,Object> generalSync(String name, int requestType, HashMap<String,Object> extensions)
			throws GetDNSException;

	void close();

}
