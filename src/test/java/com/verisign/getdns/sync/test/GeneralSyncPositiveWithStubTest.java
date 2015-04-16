package com.verisign.getdns.sync.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;

import org.junit.Test;

import com.verisign.getdns.ContextOptionName;
import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.GetDNSUtil;
import com.verisign.getdns.IGetDNSContextSync;
import com.verisign.getdns.RRType;
import com.verisign.getdns.test.IGetDNSTestConstants;

public class GeneralSyncPositiveWithStubTest implements IGetDNSTestConstants {

	/*
	 * check unit test case for A Record
	 */

	@Test
	public void testGetDNSSyncForARecord() {
		HashMap<ContextOptionName, Object> options = new HashMap<ContextOptionName, Object>();
		options.put(ContextOptionName.STUB, true);
		final IGetDNSContextSync context = GetDNSFactory.createSync(1,options);
		try {

			HashMap<String, Object> info = context.generalSync(DOMAIN_NAME, RRType.A, null);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			// assertNotNull("Type is null and response was "+info, gettype(info));
			assertEquals(RRType.A.getValue(), GetDNSUtil.getObject(info, "/replies_tree[0]/answer[0]/type"));

		} finally {
			context.close();
		}
	}

	/*
	 * check unit test case for AAAA Record
	 */

	@Test
	public void testGetDNSSyncForAAAARecord() {

		HashMap<ContextOptionName, Object> options = new HashMap<ContextOptionName, Object>();
		options.put(ContextOptionName.STUB, true);
		final IGetDNSContextSync context = GetDNSFactory.createSync(1,options);
		try {
			HashMap<String, Object> info = context.generalSync(DOMAIN_NAME, RRType.AAAA, null);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			// assertNotNull("Type is null and response was "+info, gettype(info));
			assertEquals(RRType.AAAA.getValue(), GetDNSUtil.getObject(info, "/replies_tree[0]/answer[0]/type"));

		} finally {
			context.close();
		}
	}

	/*
	 * check unit test case for txt Record
	 */
	@Test
	public void testGetDNSSyncForTXTRecord() {
		HashMap<ContextOptionName, Object> options = new HashMap<ContextOptionName, Object>();
		options.put(ContextOptionName.STUB, true);
		final IGetDNSContextSync context = GetDNSFactory.createSync(1,options);
		try {

			HashMap<String, Object> info = context.generalSync(DOMAIN_NAME, RRType.TXT, null);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			// assertNotNull("Type is null and response was "+info, gettype(info));

			assertEquals(RRType.TXT.getValue(), GetDNSUtil.getObject(info, "/replies_tree[0]/answer[0]/type"));

		} finally {
			context.close();
		}
	}

	/*
	 * check unit test case for A Record
	 */

	@Test
	public void testGetDNSSyncForMXRecord() {
		HashMap<ContextOptionName, Object> options = new HashMap<ContextOptionName, Object>();
		options.put(ContextOptionName.STUB, true);
		final IGetDNSContextSync context = GetDNSFactory.createSync(1,options);
		try {
			HashMap<String, Object> info = context.generalSync(DOMAIN_NAME, RRType.MX, null);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			// assertNotNull("Type is null and response was "+info, gettype(info));
			assertEquals(RRType.MX.getValue(), GetDNSUtil.getObject(info, "/replies_tree[0]/answer[0]/type"));

		} finally {
			context.close();
		}
	}

}
