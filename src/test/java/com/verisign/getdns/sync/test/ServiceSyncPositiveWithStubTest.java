package com.verisign.getdns.sync.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;

import org.junit.Test;

import com.verisign.getdns.ContextOptionName;
import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.GetDNSUtil;
import com.verisign.getdns.IGetDNSContext;
import com.verisign.getdns.RRType;
import com.verisign.getdns.test.IGetDNSTestConstants;

public class ServiceSyncPositiveWithStubTest implements IGetDNSTestConstants {

	@Test
	public void testGetService() {
		HashMap<ContextOptionName, Object> options = new HashMap<ContextOptionName, Object>();
		options.put(ContextOptionName.STUB, true);
		final IGetDNSContext context = GetDNSFactory.create(1, options);
		try {

			HashMap<String, Object> info = context.serviceSync("_xmpp-server._tcp.google.com", null);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			// assertNotNull("Type is null and response was "+info, gettype(info));
			assertEquals(RRType.SRV.getValue(), GetDNSUtil.getObject(info, "/replies_tree[0]/answer[0]/type"));

		} finally {
			context.close();
		}
	}

}
