package com.verisign.getdns;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

public class ServiceAsyncPositiveTest implements IGetDNSTestConstants {

	@Test
	public void testGetDNSService() throws ExecutionException, TimeoutException, InterruptedException {

		final IGetDNSContext context = GetDNSFactory.create(1);

		try {
			GetDNSFutureResult futureResult = context.serviceAsync("_xmpp-server._tcp.verisign.com.", null);
			HashMap<String, Object> info = null;
			try {
				info = futureResult.get(5000, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			assertEquals(RRType.SRV.getValue(), GetDNSUtil.getinfovalues(info, "type"));
			System.out.println("Got a service record");
		} finally {
			Thread.sleep(5000);
			context.close();
		}
	}

	@Test
	public void testGetDNSNXDDomain() throws ExecutionException, TimeoutException {

		final IGetDNSContext context = GetDNSFactory.create(1);

		try {
			GetDNSFutureResult futureResult = context.serviceAsync(UNREGDOMAIN, null);
			HashMap<String, Object> info = null;
			try {
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
