package com.verisign.getdns.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;

import org.junit.Test;

import com.verisign.getdns.ContextOptionName;
import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.GetDNSUtil;
import com.verisign.getdns.IGetDNSContext;
import com.verisign.getdns.RRType;

public class AddressSyncPositiveWithStubTest implements IGetDNSTestConstants {

	// @Test
	public void testGetDNSAddrForlocalhost() {
		HashMap<ContextOptionName, Object> options = new HashMap<ContextOptionName, Object>();
		options.put(ContextOptionName.STUB, true);
		final IGetDNSContext context = GetDNSFactory.create(1,options);
		try {

			HashMap<String, Object> info = context.addressSync("localhost", null);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			// assertNotNull("Type is null and response was "+info, gettype(info));
			assertEquals(RRType.A.getValue(), GetDNSUtil.getinfovalues(info, "type"));

		} finally {
			context.close();
		}
	}

	@Test
	public void testGetDNSAddrUnboundDomainZone() {
		HashMap<ContextOptionName, Object> options = new HashMap<ContextOptionName, Object>();
		options.put(ContextOptionName.STUB, true);
		final IGetDNSContext context = GetDNSFactory.create(1,options);
		try {

			HashMap<String, Object> info = context.addressSync(DOMAIN_NAME_FROM_UNBOUND_ZONE, null);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			// assertNotNull("Type is null and response was "+info, gettype(info));
			assertEquals(RRType.A.getValue(), GetDNSUtil.getinfovalues(info, "type"));

		} finally {
			context.close();
		}
	}

	@Test
	public void testGetDNAddr() {
		HashMap<ContextOptionName, Object> options = new HashMap<ContextOptionName, Object>();
		options.put(ContextOptionName.STUB, true);
		final IGetDNSContext context = GetDNSFactory.create(1,options);
		try {

			HashMap<String, Object> info = context.addressSync(DOMAIN_NAME, null);
			System.out.println("info:  " + info);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			// assertNotNull("Type is null and response was "+info, gettype(info));
			assertEquals(RRType.A.getValue(), GetDNSUtil.getinfovalues(info, "type"));

		} finally {
			context.close();
		}
	}

}
