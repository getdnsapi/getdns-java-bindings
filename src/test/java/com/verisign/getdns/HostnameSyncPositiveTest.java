package com.verisign.getdns;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;

import org.junit.Test;

public class HostnameSyncPositiveTest{
	
	

	@Test
	public void testGetHostname(){
		final IGetDNSContext context = GetDNSFactory.create(1);		
		try{
			HashMap<String, Object> address = new HashMap<>();
			address.put("address_type", "IPv4");
			address.put("address_data", "\u0008\u0008\u0008\u0008");
			HashMap<String, Object> info = context.hostnameSync(address , null);
			assertNotNull(info);
			assertEquals("Time out error"+info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			//assertNotNull("Type is null and response was "+info, gettype(info));
//			assertEquals(RRType.GETDNS_RRTYPE_A.getValue(),Integer.parseInt(gettype(info)));
		
			 
		}finally {
			context.close();
		}
	}
}
