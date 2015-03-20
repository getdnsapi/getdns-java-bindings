package com.verisign.getdns;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.UnknownHostException;
import java.util.HashMap;

import org.junit.Test;

public class HostnameSyncPositiveTest{
	
	

    @Test
	public void testGetHostnameIPV6() throws UnknownHostException{
    	HashMap<String, Object> options = new HashMap<String, Object>();
			options.put(GetDNSConstants.CONTEXT_SET_STUB,true);
		final IGetDNSContext context = GetDNSFactory.create(1,options);		
		try{
			HashMap<String, Object> info = context.hostnameSync("2001:4860:4860::8888" , null);
			System.out.println(info);
			assertNotNull(info);
			assertEquals("Time out error"+info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			assertEquals(RRType.GETDNS_RRTYPE_PTR.getValue(),GetDNSUtil.getinfovalues(info, "type"));
		
			 
		}finally {
			context.close();
		}
	}
	
	
	@Test
	public void testGetHostnameINPV4() throws UnknownHostException{
		 HashMap<String, Object> options = new HashMap<String, Object>();
			options.put(GetDNSConstants.CONTEXT_SET_STUB,true);
		final IGetDNSContext context = GetDNSFactory.create(1,options);		
		try{
			
			HashMap<String, Object> info = context.hostnameSync("8.8.8.8" , null);
			System.out.println(info);
			assertNotNull(info);
			assertEquals("Time out error"+info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			assertEquals(RRType.GETDNS_RRTYPE_PTR.getValue(),GetDNSUtil.getinfovalues(info, "type"));
		
			 
		}finally {
			context.close();
		}
	}
}
