package com.verisign.getdns;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;
public class HostnameAsyncPositiveTest {

	
	//@Test
	public void testGetHostnameIPV4() throws UnknownHostException, ExecutionException, TimeoutException{
		final IGetDNSContext context = GetDNSFactory.create(1);		
		try{
			GetDNSFutureResult futureResult = context.hostnameAsync("8.8.4.4" , null);
			HashMap<String, Object> info = null;
			try {
				info = futureResult.get(5000, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(info);
			assertNotNull(info);
			assertEquals("Time out error"+info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			assertEquals(RRType.GETDNS_RRTYPE_A.getValue(),Integer.parseInt(GetDNSUtil.gettype(info)));
		}finally {
			context.close();
		}
		
	}
	
	
	
//	@Test
	public void testGetHostnameIPV6() throws UnknownHostException, ExecutionException, TimeoutException{
		final IGetDNSContext context = GetDNSFactory.create(1);		
		try{
			GetDNSFutureResult futureResult = context.hostnameAsync("2001:4860:4860::8888" , null);
			HashMap<String, Object> info = null;
			try {
				info = futureResult.get(5000, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(info);
			assertNotNull(info);
			assertEquals("Time out error"+info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			assertEquals(RRType.GETDNS_RRTYPE_A.getValue(),Integer.parseInt(GetDNSUtil.gettype(info)));
		}finally {
			context.close();
		}
		
	}
}
