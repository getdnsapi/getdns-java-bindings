package com.verisign.getdns.sync.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.UnknownHostException;
import java.util.HashMap;

import org.junit.Test;

import com.verisign.getdns.ContextOptionName;
import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.GetDNSUtil;
import com.verisign.getdns.IGetDNSContextSync;
import com.verisign.getdns.RRType;

public class HostnameSyncPositiveWithRecursiveTest {

	@Test
	public void testGetHostnameIPV6() throws UnknownHostException {
		HashMap<ContextOptionName, Object> options = new HashMap<ContextOptionName, Object>();
		options.put(ContextOptionName.STUB, false);
		final IGetDNSContextSync context = GetDNSFactory.createSync(1,options);
		try {
			HashMap<String, Object> info = context.hostnameSync("2001:4860:4860::8888", null);
			System.out.println(info);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			assertEquals(RRType.PTR.getValue(), GetDNSUtil.getObject(info, "/replies_tree[0]/answer[0]/type"));

		} finally {
			context.close();
		}
	}

	@Test
	public void testGetHostnameINPV4() throws UnknownHostException {
		HashMap<ContextOptionName, Object> options = new HashMap<ContextOptionName, Object>();
		options.put(ContextOptionName.STUB, false);
		final IGetDNSContextSync context = GetDNSFactory.createSync(1,options);
		try {

			HashMap<String, Object> info = context.hostnameSync("8.8.8.8", null);
			System.out.println(info);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			assertEquals(RRType.PTR.getValue(), GetDNSUtil.getObject(info, "/replies_tree[0]/answer[0]/type"));

		} finally {
			context.close();
		}
	}
}
