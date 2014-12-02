package com.verisign.getdns;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

public class HostnameSyncPositiveTest{
	
	

  //  @Test
	public void testGetHostnameIPV6() throws UnknownHostException{
		final IGetDNSContext context = GetDNSFactory.create(1);		
		try{
			HashMap<String, Object> info = context.hostnameSync("2001:4860:4860::8888" , null);
			System.out.println(info);
			assertNotNull(info);
			assertEquals("Time out error"+info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			//assertNotNull("Type is null and response was "+info, gettype(info));
//			assertEquals(RRType.GETDNS_RRTYPE_A.getValue(),Integer.parseInt(gettype(info)));
		
			 
		}finally {
			context.close();
		}
	}
	
	
	//@Test
	public void testGetHostnameINPV4() throws UnknownHostException{
		final IGetDNSContext context = GetDNSFactory.create(1);		
		try{
			HashMap<String, Object> info = context.hostnameSync("2001:4860:4860::8888" , null);
			System.out.println(info);
			assertNotNull(info);
			assertEquals("Time out error"+info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			//assertNotNull("Type is null and response was "+info, gettype(info));
//			assertEquals(RRType.GETDNS_RRTYPE_A.getValue(),Integer.parseInt(gettype(info)));
		
			 
		}finally {
			context.close();
		}
	}
}
