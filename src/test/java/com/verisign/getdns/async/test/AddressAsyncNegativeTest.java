package com.verisign.getdns.async.test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.verisign.getdns.GetDNSException;
import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.IGetDNSContext;
import com.verisign.getdns.RRType;
import com.verisign.getdns.test.ErrorCodeMatcher;
import com.verisign.getdns.test.IGetDNSTestConstants;

/*
 * 
 */
public class AddressAsyncNegativeTest implements IGetDNSTestConstants {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testGetDNSSyncNonExistingDomain() throws ExecutionException, TimeoutException, InterruptedException {

		final IGetDNSContext context = GetDNSFactory.create(1);
		try {
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_INVALID_PARAMETER"));
			context.generalAsync(null, RRType.A, null);
			context.run();
		} finally {
			context.close();
		}

	}

	/*
	 * check for response for null domain
	 */

	@Test
	public void testGetDNSSyncNULLDomain() {

		final IGetDNSContext context = GetDNSFactory.create(1);
		try {
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_INVALID_PARAMETER"));
			context.addressAsync(null, null);
			context.run();
		} finally {
			context.close();
		}

	}

	/*
	 * check unit test case against invalid domain (label too long)
	 */

	@Test
	public void testGetDNSSyncLongDomain() {

		final IGetDNSContext context = GetDNSFactory.create(1);
		try {

			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_BAD_DOMAIN_NAME"));
			context.addressAsync(TOOLONGDOMAINNAME, null);
			context.run();

		} finally {
			context.close();
		}
	}

	@Test
	public void testGetDNSSyncForTooManyOctets() {

		final IGetDNSContext context = GetDNSFactory.create(1);
		try {

			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_BAD_DOMAIN_NAME"));
			context.addressAsync(TOOMANYOCTETS, null);
			context.run();

		} finally {
			context.close();
		}
	}

}
