package com.verisign.getdns;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class GeneralAsyncNegitiveTest implements IGetDNSTestConstants{
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();

	
	
	@Test
	public void testGetDNSASyncNULLDomain() throws ExecutionException, TimeoutException  {
		
		final IGetDNSContext context = GetDNSFactory.create(1);		
	
		try{
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_INVALID_PARAMETER"));
			GetDNSFutureResult futureResult = context.generalAsync(null, RRType.A, null);
		
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
	public void testGetDNSASyncLongDomain(){
	
		final IGetDNSContext context = GetDNSFactory.create(1);		
		try{
			
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_BAD_DOMAIN_NAME"));
			context.generalAsync(TOOLONGDOMAINNAME, RRType.A,  null);
			 
		}finally {
			context.close();
		}
	}
	

	@Test
	public void testGetDNSSyncForTooManyOctets(){
		System.out.println("Junit 3");
		final IGetDNSContext context = GetDNSFactory.create(1);		
		try{
			
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_BAD_DOMAIN_NAME"));
			context.generalAsync(TOOMANYOCTETS,RRType.A, null);
			 
		}finally {
			context.close();
		}
	}

}
