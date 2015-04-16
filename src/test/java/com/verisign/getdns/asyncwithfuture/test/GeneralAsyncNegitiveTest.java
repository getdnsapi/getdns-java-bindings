package com.verisign.getdns.asyncwithfuture.test;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.verisign.getdns.GetDNSException;
import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.GetDNSFutureResult;
import com.verisign.getdns.IGetDNSContextAsyncWithFuture;
import com.verisign.getdns.RRType;
import com.verisign.getdns.test.ErrorCodeMatcher;
import com.verisign.getdns.test.IGetDNSTestConstants;

public class GeneralAsyncNegitiveTest implements IGetDNSTestConstants{
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();

	
	
	@Test
	public void testGetDNSASyncNULLDomain() throws ExecutionException, TimeoutException  {
		
		final IGetDNSContextAsyncWithFuture context = GetDNSFactory.createAsyncWithFuture(1,null);	
	
		try{
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_INVALID_PARAMETER"));
			GetDNSFutureResult futureResult = context.generalAsync(null, RRType.A, null);
		
			try {
				context.run();
				futureResult.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}finally {
			context.close();
		}
	}
	
	@Test
	public void testGetDNSASyncLongDomain(){
	
		final IGetDNSContextAsyncWithFuture context = GetDNSFactory.createAsyncWithFuture(1,null);
		try{
			
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_BAD_DOMAIN_NAME"));
			context.generalAsync(TOOLONGDOMAINNAME, RRType.A,  null);
			context.run();
			 
		}finally {
			context.close();
		}
	}
	

	@Test
	public void testGetDNSSyncForTooManyOctets(){
		final IGetDNSContextAsyncWithFuture context = GetDNSFactory.createAsyncWithFuture(1,null);		
		try{
			
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_BAD_DOMAIN_NAME"));
			context.generalAsync(TOOMANYOCTETS,RRType.A, null);
			context.run();
			 
		}finally {
			context.close();
		}
	}

}
