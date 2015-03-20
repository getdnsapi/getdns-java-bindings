package com.verisign.getdns;

import java.util.HashMap;

public interface IGetDNSCallback {
	void handleResponse(HashMap<String, Object> response);

	void handleException(GetDNSException exception);
}
