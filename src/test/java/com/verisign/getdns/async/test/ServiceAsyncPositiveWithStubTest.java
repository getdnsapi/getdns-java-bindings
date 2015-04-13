package com.verisign.getdns.async.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

import com.verisign.getdns.ContextOptionName;
import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.GetDNSFutureResult;
import com.verisign.getdns.GetDNSUtil;
import com.verisign.getdns.IGetDNSContext;
import com.verisign.getdns.RRType;
import com.verisign.getdns.test.IGetDNSTestConstants;

public class ServiceAsyncPositiveWithStubTest implements IGetDNSTestConstants {

	@Test
	public void testGetDNSService() throws ExecutionException, TimeoutException, InterruptedException {
		HashMap<ContextOptionName, Object> options = new HashMap<ContextOptionName, Object>();
		options.put(ContextOptionName.STUB, true);
		final IGetDNSContext context = GetDNSFactory.create(1, options);

		try {
			GetDNSFutureResult futureResult = context.serviceAsync("_xmpp-server._tcp.google.com", null);
			HashMap<String, Object> info = null;
			try {
				context.run();
				info = futureResult.get(5000, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			assertEquals(RRType.SRV.getValue(), GetDNSUtil.getObject(info, "/replies_tree[0]/answer[0]/type"));
			System.out.println("Got a service record");
		} finally {
			Thread.sleep(5000);
			context.close();
		}
	}

	@Test
	public void testGetDNSNXDDomain() throws ExecutionException, TimeoutException {
		HashMap<ContextOptionName, Object> options = new HashMap<ContextOptionName, Object>();
		options.put(ContextOptionName.STUB, true);
		final IGetDNSContext context = GetDNSFactory.create(1, options);

		try {
			GetDNSFutureResult futureResult = context.serviceAsync(UNREGDOMAIN, null);
			HashMap<String, Object> info = null;
			try {
				context.run();
				info = futureResult.get(5000, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(info);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 901, Integer.parseInt(info.get("status").toString()));
		} finally {
			context.close();
		}
	}

}
