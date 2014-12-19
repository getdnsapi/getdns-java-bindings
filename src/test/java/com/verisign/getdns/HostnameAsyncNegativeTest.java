package com.verisign.getdns;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class HostnameAsyncNegativeTest {

	@Rule
    public ExpectedException thrown = ExpectedException.none();
	@Test
	public void testGetHostnameNULL() throws UnknownHostException, ExecutionException, TimeoutException{
		final IGetDNSContext context = GetDNSFactory.create(1);		
		try{
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_BAD_DOMAIN_NAME"));
			GetDNSFutureResult futureResult = context.hostnameAsync(null , null);
			System.out.println(futureResult);
			
		}finally {
			context.close();
		}
		
	}
}
