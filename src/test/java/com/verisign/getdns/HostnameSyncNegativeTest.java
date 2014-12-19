package com.verisign.getdns;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.UnknownHostException;
import java.util.HashMap;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class HostnameSyncNegativeTest {
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	 @Test
		public void testGetHostnameNUll() throws UnknownHostException{
			final IGetDNSContext context = GetDNSFactory.create(1);		
			try{
				thrown.expect(GetDNSException.class);				
				thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_BAD_DOMAIN_NAME"));
				HashMap<String, Object> info = context.hostnameSync(null , null);
				System.out.println(info);
				
			
				 
			}finally {
				context.close();
			}
		}

}