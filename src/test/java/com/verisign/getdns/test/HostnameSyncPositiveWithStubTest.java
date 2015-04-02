package com.verisign.getdns.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.UnknownHostException;
import java.util.HashMap;

import org.junit.Test;

import com.verisign.getdns.ContextOptionName;
import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.GetDNSUtil;
import com.verisign.getdns.IGetDNSContext;
import com.verisign.getdns.RRType;

public class HostnameSyncPositiveWithStubTest {

	@Test
	public void testGetHostnameIPV6() throws UnknownHostException {
		HashMap<ContextOptionName, Object> options = new HashMap<ContextOptionName, Object>();
		options.put(ContextOptionName.STUB, true);
		final IGetDNSContext context = GetDNSFactory.create(1,options);
		try {
			HashMap<String, Object> info = context.hostnameSync("2001:4860:4860::8888", null);
			System.out.println(info);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			assertEquals(RRType.PTR.getValue(), GetDNSUtil.getinfovalues(info, "type"));

		} finally {
			context.close();
		}
	}

	@Test
	public void testGetHostnameINPV4() throws UnknownHostException {
		final IGetDNSContext context = GetDNSFactory.create(1);
		try {

			HashMap<String, Object> info = context.hostnameSync("8.8.8.8", null);
			System.out.println(info);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			assertEquals(RRType.PTR.getValue(), GetDNSUtil.getinfovalues(info, "type"));

		} finally {
			context.close();
		}
	}
}
