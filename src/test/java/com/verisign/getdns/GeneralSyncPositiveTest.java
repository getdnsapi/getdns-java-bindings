package com.verisign.getdns;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;

import org.junit.Test;

public class GeneralSyncPositiveTest implements IGetDNSTestConstants {

	/*
	 * check unit test case for A Record
	 */

	@Test
	public void testGetDNSSyncForARecord() {
		final IGetDNSContext context = GetDNSFactory.create(1);
		try {

			HashMap<String, Object> info = context.generalSync(DOMAIN_NAME, RRType.GETDNS_RRTYPE_A, null);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			// assertNotNull("Type is null and response was "+info, gettype(info));
			assertEquals(RRType.GETDNS_RRTYPE_A.getValue(), GetDNSUtil.getinfovalues(info, "type"));

		} finally {
			context.close();
		}
	}

	/*
	 * check unit test case for AAAA Record
	 */

	@Test
	public void testGetDNSSyncForAAAARecord() {

		final IGetDNSContext context = GetDNSFactory.create(1);
		try {
			HashMap<String, Object> info = context.generalSync(DOMAIN_NAME, RRType.GETDNS_RRTYPE_AAAA, null);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			// assertNotNull("Type is null and response was "+info, gettype(info));
			assertEquals(RRType.GETDNS_RRTYPE_AAAA.getValue(), GetDNSUtil.getinfovalues(info, "type"));

		} finally {
			context.close();
		}
	}

	/*
	 * check unit test case for txt Record
	 */
	@Test
	public void testGetDNSSyncForTXTRecord() {

		final IGetDNSContext context = GetDNSFactory.create(1);
		try {

			HashMap<String, Object> info = context.generalSync(DOMAIN_NAME, RRType.GETDNS_RRTYPE_TXT, null);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			// assertNotNull("Type is null and response was "+info, gettype(info));

			assertEquals(RRType.GETDNS_RRTYPE_TXT.getValue(), GetDNSUtil.getinfovalues(info, "type"));

		} finally {
			context.close();
		}
	}

	/*
	 * check unit test case for A Record
	 */

	@Test
	public void testGetDNSSyncForMXRecord() {
		final IGetDNSContext context = GetDNSFactory.create(1);
		try {
			HashMap<String, Object> info = context.generalSync(DOMAIN_NAME, RRType.GETDNS_RRTYPE_MX, null);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			// assertNotNull("Type is null and response was "+info, gettype(info));
			assertEquals(RRType.GETDNS_RRTYPE_MX.getValue(), GetDNSUtil.getinfovalues(info, "type"));

		} finally {
			context.close();
		}
	}

}
