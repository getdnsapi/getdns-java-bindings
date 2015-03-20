package com.verisign.getdns;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class HostnameAsyncNegativeTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testGetHostnameNULL() throws Exception {
		final IGetDNSContext context = GetDNSFactory.create(1);
		try {
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_INVALID_PARAMETER"));
			GetDNSFutureResult futureResult = context.hostnameAsync(null, null);
			HashMap<String, Object> info = null;
			info = futureResult.get(5000, TimeUnit.MILLISECONDS);
		} finally {
			context.close();
		}

	}
}
