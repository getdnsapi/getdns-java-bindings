package com.verisign.getdns;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;

import org.junit.Test;

public class ServiceSyncPositiveTest implements IGetDNSTestConstants {

	@Test
	public void testGetService() {
		HashMap<String, Object> options = new HashMap<String, Object>();
		//options.put(GetDNSConstants.CONTEXT_SET_STUB, true);
		options.put(GetDNSConstants.CONTEXT_SET_TIMEOUT, 2000);
		final IGetDNSContext context = GetDNSFactory.create(1);
		try {

			HashMap<String, Object> info = context.serviceSync("_xmpp-server._tcp.verisign.com.", null);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			// assertNotNull("Type is null and response was "+info, gettype(info));
			assertEquals(RRType.GETDNS_RRTYPE_SRV.getValue(), GetDNSUtil.getinfovalues(info, "type"));

		} finally {
			context.close();
		}
	}

}
