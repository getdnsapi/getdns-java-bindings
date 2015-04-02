package com.verisign.getdns;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

public class AddressAsyncPositiveTest implements IGetDNSTestConstants {

	// @Test
	public void testGetDNSAddrForlocalhost() throws ExecutionException, TimeoutException {
		final IGetDNSContext context = GetDNSFactory.create(1);

		try {
			GetDNSFutureResult futureResult = context.addressAsync("localhost", null);
			HashMap<String, Object> info = null;
			try {
				info = futureResult.get(5000, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(info);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			assertEquals(RRType.A.getValue(), Integer.parseInt(GetDNSUtil.getinfovalues(info, "type").toString()));
		} finally {
			context.close();
		}

	}

	@Test
	public void testGetDNSAddrUnboundDomainZone() throws ExecutionException, TimeoutException {
		final IGetDNSContext context = GetDNSFactory.create(1);

		try {
			GetDNSFutureResult futureResult = context.addressAsync(DOMAIN_NAME_FROM_UNBOUND_ZONE, null);
			HashMap<String, Object> info = null;
			try {
				info = futureResult.get(5000, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(info);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			assertEquals(RRType.A.getValue(),GetDNSUtil.getinfovalues(info, "type"));
		} finally {
			context.close();
		}

	}

	@Test
	public void testGetDNAddr() throws ExecutionException, TimeoutException {
		final IGetDNSContext context = GetDNSFactory.create(1);

		try {
			GetDNSFutureResult futureResult = context.addressAsync(DOMAIN_NAME, null);
			HashMap<String, Object> info = null;
			try {
				info = futureResult.get(5000, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(info);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			assertEquals(RRType.A.getValue(), GetDNSUtil.getinfovalues(info, "type"));
		} finally {
			context.close();
		}

	}

}
