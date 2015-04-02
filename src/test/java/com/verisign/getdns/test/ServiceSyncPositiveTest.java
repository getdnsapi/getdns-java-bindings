package com.verisign.getdns.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;

import org.junit.Test;

import com.verisign.getdns.ContextOptionNames;
import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.GetDNSUtil;
import com.verisign.getdns.IGetDNSContext;
import com.verisign.getdns.RRType;

public class ServiceSyncPositiveTest implements IGetDNSTestConstants {

	@Test
	public void testGetService() {
		HashMap<ContextOptionNames, Object> options = new HashMap<ContextOptionNames, Object>();
		options.put(ContextOptionNames.TIMEOUT, 2000);
		final IGetDNSContext context = GetDNSFactory.create(1, options);
		try {

			HashMap<String, Object> info = context.serviceSync("_xmpp-server._tcp.verisign.com.", null);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			// assertNotNull("Type is null and response was "+info, gettype(info));
			assertEquals(RRType.SRV.getValue(), GetDNSUtil.getinfovalues(info, "type"));

		} finally {
			context.close();
		}
	}

}
