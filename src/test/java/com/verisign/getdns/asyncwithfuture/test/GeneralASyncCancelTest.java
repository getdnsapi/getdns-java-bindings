package com.verisign.getdns.asyncwithfuture.test;

import static com.verisign.getdns.test.IGetDNSTestConstants.DOMAIN_NAME;
import static org.junit.Assert.assertEquals;

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

public class GeneralASyncCancelTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testGetDNSAsyncWithCancel() throws ExecutionException, TimeoutException {
		final IGetDNSContextAsyncWithFuture context = GetDNSFactory.createAsyncWithFuture(1,null);

		try {
			GetDNSFutureResult futureResult = context.generalAsync(DOMAIN_NAME, RRType.A, null);
			futureResult.cancel(true);
			context.run();
			assertEquals(true, futureResult.isCancelled());
		} finally {
			context.close();
		}
	}

	@Test
	public void testGetDNSAsyncWithCancel1() throws ExecutionException, TimeoutException {
		final IGetDNSContextAsyncWithFuture context = GetDNSFactory.createAsyncWithFuture(1,null);

		try {
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_UNKNOWN_TRANSACTION"));
			GetDNSFutureResult futureResult = context.generalAsync(DOMAIN_NAME, RRType.A, null);
			futureResult.cancel(true);
			context.run();
			futureResult.cancel(true);

		} finally {
			context.close();
		}
	}

	@Test
	public void testGetDNSAsyncWithCancel2() throws ExecutionException, TimeoutException, InterruptedException {
		final IGetDNSContextAsyncWithFuture context = GetDNSFactory.createAsyncWithFuture(1,null);

		try {
			thrown.expect(java.util.concurrent.CancellationException.class);
			thrown.expectMessage("This request is already cancelled");
			GetDNSFutureResult futureResult = context.generalAsync(DOMAIN_NAME, RRType.A, null);
			futureResult.cancel(true);
			context.run();
			futureResult.get();
		} finally {
			context.close();
		}
	}
}
