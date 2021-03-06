package com.verisign.getdns.sync.test;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.verisign.getdns.GetDNSException;
import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.IGetDNSContextSync;
import com.verisign.getdns.test.ErrorCodeMatcher;
import com.verisign.getdns.test.IGetDNSTestConstants;

/*
 * 
 */
public class ServiceSyncNegativeTest implements IGetDNSTestConstants {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	/**
	 * check for response for null domain
	 */
	@Test
	public void testGetDNSSyncNULLDomain() {

		final IGetDNSContextSync context = GetDNSFactory.createSync(1, null);
		try {
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_INVALID_PARAMETER"));
			context.serviceSync(null, null);
		} finally {
			context.close();
		}

	}

	/**
	 * check unit test case against invalid domain (label too long)
	 */

	@Test
	public void testGetDNSSyncLongDomain() {

		final IGetDNSContextSync context = GetDNSFactory.createSync(1, null);
		try {

			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_BAD_DOMAIN_NAME"));
			context.serviceSync(TOOLONGDOMAINNAME, null);

		} finally {
			context.close();
		}
	}

	@Test
	public void testGetDNSSyncForTooManyOctets() {
		final IGetDNSContextSync context = GetDNSFactory.createSync(1, null);
		try {

			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_BAD_DOMAIN_NAME"));
			context.serviceSync(TOOMANYOCTETS, null);

		} finally {
			context.close();
		}
	}

}
