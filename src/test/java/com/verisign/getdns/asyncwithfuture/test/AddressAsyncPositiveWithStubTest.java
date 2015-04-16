package com.verisign.getdns.asyncwithfuture.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

import com.verisign.getdns.ContextOptionName;
import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.GetDNSFutureResult;
import com.verisign.getdns.GetDNSUtil;
import com.verisign.getdns.IGetDNSContextAsyncWithFuture;
import com.verisign.getdns.RRType;
import com.verisign.getdns.test.IGetDNSTestConstants;

public class AddressAsyncPositiveWithStubTest implements IGetDNSTestConstants {

	// @Test
	public void testGetDNSAddrForlocalhost() throws ExecutionException, TimeoutException {
		HashMap<ContextOptionName, Object> options = new HashMap<ContextOptionName, Object>();
		options.put(ContextOptionName.STUB, true);
		final IGetDNSContextAsyncWithFuture context = GetDNSFactory.createAsyncWithFuture(1,options);

		try {
			GetDNSFutureResult futureResult = context.addressAsync("localhost", null);
			HashMap<String, Object> info = null;
			try {
				context.run();
				info = futureResult.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(info);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			assertEquals(RRType.A.getValue(),
					Integer.parseInt(GetDNSUtil.getObject(info, "/replies_tree[0]/authority[0]/type").toString()));
		} finally {
			context.close();
		}

	}

	@Test
	public void testGetDNSAddrUnboundDomainZone() throws ExecutionException, TimeoutException {
		HashMap<ContextOptionName, Object> options = new HashMap<ContextOptionName, Object>();
		options.put(ContextOptionName.STUB, true);
		final IGetDNSContextAsyncWithFuture context = GetDNSFactory.createAsyncWithFuture(1,options);

		try {
			GetDNSFutureResult futureResult = context.addressAsync(DOMAIN_NAME_FROM_UNBOUND_ZONE, null);
			HashMap<String, Object> info = null;
			try {
				context.run();
				info = futureResult.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(info);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			assertEquals(RRType.A.getValue(), GetDNSUtil.getObject(info, "/replies_tree[0]/answer[0]/type"));
		} finally {
			context.close();
		}

	}

	@Test
	public void testGetDNAddr() throws ExecutionException, TimeoutException {
		HashMap<ContextOptionName, Object> options = new HashMap<ContextOptionName, Object>();
		options.put(ContextOptionName.STUB, true);
		final IGetDNSContextAsyncWithFuture context = GetDNSFactory.createAsyncWithFuture(1,options);

		try {
			GetDNSFutureResult futureResult = context.addressAsync(DOMAIN_NAME, null);
			HashMap<String, Object> info = null;
			try {
				context.run();
				info = futureResult.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(info);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			assertEquals(RRType.A.getValue(), GetDNSUtil.getObject(info, "/replies_tree[0]/answer[0]/type"));
		} finally {
			context.close();
		}

	}

}
