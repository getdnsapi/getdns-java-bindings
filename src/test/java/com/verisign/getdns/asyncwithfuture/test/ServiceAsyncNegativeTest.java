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
import com.verisign.getdns.test.ErrorCodeMatcher;
import com.verisign.getdns.test.IGetDNSTestConstants;

/*
 * 
 */
public class ServiceAsyncNegativeTest implements IGetDNSTestConstants {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testGetDNSAsyncNULLDomain() throws ExecutionException, TimeoutException {

		final IGetDNSContextAsyncWithFuture context = GetDNSFactory.createAsyncWithFuture(1, null);

		try {
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_INVALID_PARAMETER"));
			GetDNSFutureResult futureResult = context.serviceAsync(null, null);

			try {
				context.run();
				futureResult.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} finally {
			context.close();
		}
	}

	@Test
	public void testGetDNSAsyncLongDomain() {

		final IGetDNSContextAsyncWithFuture context = GetDNSFactory.createAsyncWithFuture(1, null);
		try {

			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_BAD_DOMAIN_NAME"));
			context.run();
			context.serviceAsync(TOOLONGDOMAINNAME, null);

		} finally {
			context.close();
		}
	}

	@Test
	public void testGetDNSAsyncForTooManyOctets() {
		System.out.println("Junit 3");
		final IGetDNSContextAsyncWithFuture context = GetDNSFactory.createAsyncWithFuture(1, null);
		try {

			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_BAD_DOMAIN_NAME"));
			context.run();
			context.serviceAsync(TOOMANYOCTETS, null);

		} finally {
			context.close();
		}
	}

}
