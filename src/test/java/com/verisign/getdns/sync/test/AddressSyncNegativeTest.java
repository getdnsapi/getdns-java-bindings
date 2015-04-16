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
public class AddressSyncNegativeTest implements IGetDNSTestConstants {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	/*
	 * check for response for null domain
	 */

	@Test
	public void testGetDNSSyncNULLDomain() {

		final IGetDNSContextSync context = GetDNSFactory.createSync(1,null);
		try {
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_INVALID_PARAMETER"));
			context.addressSync(null, null);
		} finally {
			context.close();
		}

	}

	/*
	 * check unit test case against invalid domain (label too long)
	 */

	@Test
	public void testGetDNSSyncLongDomain() {

		final IGetDNSContextSync context = GetDNSFactory.createSync(1,null);
		try {

			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_BAD_DOMAIN_NAME"));
			context.addressSync(TOOLONGDOMAINNAME, null);

		} finally {
			context.close();
		}
	}

	@Test
	public void testGetDNSSyncForTooManyOctets() {
		System.out.println("Junit 3");
		final IGetDNSContextSync context = GetDNSFactory.createSync(1,null);
		try {

			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_BAD_DOMAIN_NAME"));
			context.addressSync(TOOMANYOCTETS, null);

		} finally {
			context.close();
		}
	}

}
