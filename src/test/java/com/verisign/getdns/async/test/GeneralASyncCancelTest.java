package com.verisign.getdns.async.test;

import static com.verisign.getdns.test.IGetDNSTestConstants.DOMAIN_NAME;
import static org.junit.Assert.assertEquals;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.verisign.getdns.GetDNSException;
import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.GetDNSFutureResult;
import com.verisign.getdns.IGetDNSContext;
import com.verisign.getdns.RRType;
import com.verisign.getdns.test.ErrorCodeMatcher;

public class GeneralASyncCancelTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testGetDNSAsyncWithCancel() throws ExecutionException, TimeoutException {
		final IGetDNSContext context = GetDNSFactory.create(1);

		try {
			GetDNSFutureResult futureResult = context.generalAsync(DOMAIN_NAME, RRType.A, null);
			futureResult.cancel(true);
			assertEquals(true, futureResult.isCancelled());
		} finally {
			context.close();
		}
	}

	@Test
	public void testGetDNSAsyncWithCancel1() throws ExecutionException, TimeoutException {
		final IGetDNSContext context = GetDNSFactory.create(1);

		try {
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_UNKNOWN_TRANSACTION"));
			GetDNSFutureResult futureResult = context.generalAsync(DOMAIN_NAME, RRType.A, null);
			futureResult.cancel(true);
			futureResult.cancel(true);

		} finally {
			context.close();
		}
	}

	@Test
	public void testGetDNSAsyncWithCancel2() throws ExecutionException, TimeoutException, InterruptedException {
		final IGetDNSContext context = GetDNSFactory.create(1);

		try {
			thrown.expect(java.util.concurrent.CancellationException.class);
			thrown.expectMessage("This request is already cancelled");
			GetDNSFutureResult futureResult = context.generalAsync(DOMAIN_NAME, RRType.A, null);
			futureResult.cancel(true);
			futureResult.get(5000, TimeUnit.MILLISECONDS);
		} finally {
			context.close();
		}
	}
}
