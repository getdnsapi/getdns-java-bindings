package com.verisign.getdns;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;

import org.junit.Test;

public class AddressSyncPositiveTest implements IGetDNSTestConstants {

	// @Test
	public void testGetDNSAddrForlocalhost() {
		final IGetDNSContext context = GetDNSFactory.create(1);
		try {

			HashMap<String, Object> info = context.addressSync("localhost", null);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			// assertNotNull("Type is null and response was "+info, gettype(info));
			assertEquals(RRType.GETDNS_RRTYPE_A.getValue(), GetDNSUtil.getinfovalues(info, "type"));

		} finally {
			context.close();
		}
	}

	@Test
	public void testGetDNSAddrUnboundDomainZone() {
		final IGetDNSContext context = GetDNSFactory.create(1);
		try {

			HashMap<String, Object> info = context.addressSync(DOMAIN_NAME_FROM_UNBOUND_ZONE, null);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			// assertNotNull("Type is null and response was "+info, gettype(info));
			assertEquals(RRType.GETDNS_RRTYPE_A.getValue(), GetDNSUtil.getinfovalues(info, "type"));

		} finally {
			context.close();
		}
	}

	@Test
	public void testGetDNAddr() {
		final IGetDNSContext context = GetDNSFactory.create(1);
		try {

			HashMap<String, Object> info = context.addressSync(DOMAIN_NAME, null);
			System.out.println("info:  " + info);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			// assertNotNull("Type is null and response was "+info, gettype(info));
			assertEquals(RRType.GETDNS_RRTYPE_A.getValue(), GetDNSUtil.getinfovalues(info, "type"));

		} finally {
			context.close();
		}
	}

}
