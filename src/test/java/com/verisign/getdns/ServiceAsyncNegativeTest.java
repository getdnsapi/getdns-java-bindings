package com.verisign.getdns;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/*
 * 
 */
public class ServiceAsyncNegativeTest implements IGetDNSTestConstants{
	
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	
	
	@Test
	public void testGetDNSAsyncNULLDomain() throws ExecutionException, TimeoutException  {
		
		final IGetDNSContext context = GetDNSFactory.create(1);		
	
		try{
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_INVALID_PARAMETER"));
			GetDNSFutureResult futureResult = context.serviceAsync(null , null);
		
			try {
				futureResult.get(5000, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}finally {
			context.close();
		}
	}
	
	@Test
	public void testGetDNSAsyncLongDomain(){
	
		final IGetDNSContext context = GetDNSFactory.create(1);		
		try{
			
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_BAD_DOMAIN_NAME"));
			context.serviceAsync(TOOLONGDOMAINNAME, null);
			 
		}finally {
			context.close();
		}
	}
	

	@Test
	public void testGetDNSAsyncForTooManyOctets(){
		System.out.println("Junit 3");
		final IGetDNSContext context = GetDNSFactory.create(1);		
		try{
			
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_BAD_DOMAIN_NAME"));
			context.serviceAsync(TOOMANYOCTETS, null);
			 
		}finally {
			context.close();
		}
	}

}
