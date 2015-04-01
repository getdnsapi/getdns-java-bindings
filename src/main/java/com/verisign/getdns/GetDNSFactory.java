package com.verisign.getdns;

import java.util.HashMap;

public class GetDNSFactory {

	public static IGetDNSContext create(int setFromOs) throws GetDNSException {
		return new GetDNSContext(setFromOs);
	}

	public static IGetDNSContext create(int setFromOs, HashMap<ContextOptionNames, Object> contextOptions)
			throws GetDNSException {
		GetDNSContext context = new GetDNSContext(setFromOs);
		try {
			context.applyContextOptions(contextOptions);
		} catch (IllegalArgumentException e) {
			// TODO: Can we make exception handling more generic.
			context.close();
			throw e;
		}
		return context;
	}
}
