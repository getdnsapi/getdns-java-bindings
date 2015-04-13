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

public class AddressSyncPositiveWithStubTest implements IGetDNSTestConstants {

	// @Test
	public void testGetDNSAddrForlocalhost() {
		HashMap<ContextOptionName, Object> options = new HashMap<ContextOptionName, Object>();
		options.put(ContextOptionName.STUB, true);
		final IGetDNSContext context = GetDNSFactory.create(1, options);
		try {

			HashMap<String, Object> info = context.addressSync("localhost", null);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			assertEquals(RRType.A.getValue(), GetDNSUtil.getObject(info, "/replies_tree[0]/authority[0]/type"));

		} finally {
			context.close();
		}
	}

	@Test
	public void testGetDNSAddrUnboundDomainZone() {
		HashMap<ContextOptionName, Object> options = new HashMap<ContextOptionName, Object>();
		options.put(ContextOptionName.STUB, true);
		final IGetDNSContext context = GetDNSFactory.create(1, options);
		try {

			HashMap<String, Object> info = context.addressSync(DOMAIN_NAME_FROM_UNBOUND_ZONE, null);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			assertEquals(RRType.A.getValue(), GetDNSUtil.getObject(info, "/replies_tree[0]/answer[0]/type"));

		} finally {
			context.close();
		}
	}

	@Test
	public void testGetDNAddr() {
		HashMap<ContextOptionName, Object> options = new HashMap<ContextOptionName, Object>();
		options.put(ContextOptionName.STUB, true);
		final IGetDNSContext context = GetDNSFactory.create(1, options);
		try {

			HashMap<String, Object> info = context.addressSync(DOMAIN_NAME, null);
			System.out.println("info:  " + info);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			assertEquals(RRType.A.getValue(), GetDNSUtil.getObject(info, "/replies_tree[0]/answer[0]/type"));

		} finally {
			context.close();
		}
	}

	@Test
	public void testGetDNSSyncNonExistingDomain() {

		final IGetDNSContext context = GetDNSFactory.create(1);
		try {
			HashMap<String, Object> info = context.addressSync(UNREGDOMAIN, null);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 901, Integer.parseInt(info.get("status").toString()));
		} finally {
			context.close();
		}

	}

}
