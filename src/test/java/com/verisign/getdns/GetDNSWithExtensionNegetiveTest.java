package com.verisign.getdns;

import java.util.HashMap;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class GetDNSWithExtensionNegetiveTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	/**
	 * test with dnssec return status value
	 */
	@Test
	public void testGetDNSWithDnssecStatusExtension() {
		System.out.println("--------DNSSEC_RETURN_STATUS TEST--------------");
		final IGetDNSContext context = GetDNSFactory.create(1);
		HashMap<ExtensionNames, Object> extensions = new HashMap<ExtensionNames, Object>();
		extensions.put(ExtensionNames.DNSSEC_RETURN_STATUS, GetDNSConstants.GETDNS_EXTENSION_TRUE);
		try {
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_INVALID_PARAMETER"));
			HashMap<String, Object> info = context.generalSync(null, RRType.GETDNS_RRTYPE_A, extensions);
		} finally {
			context.close();
		}

	}

	/**
	 * test with dnssec return only secure
	 */
	@Test
	public void testGetDNSWithDnssecOnlySecureExtension() {
		System.out.println("--------DNSSEC_RETURN_ONLY_SECURE--------------");
		final IGetDNSContext context = GetDNSFactory.create(1);
		HashMap<ExtensionNames, Object> extensions = new HashMap<ExtensionNames, Object>();
		extensions.put(ExtensionNames.DNSSEC_RETURN_ONLY_SECURE, GetDNSConstants.GETDNS_EXTENSION_TRUE);
		try {
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_INVALID_PARAMETER"));
			HashMap<String, Object> info = context.generalSync(null, RRType.GETDNS_RRTYPE_A, extensions);
		} finally {
			context.close();
		}

	}

	/**
	 * test with dnssec return validation chain
	 */
	@Test
	public void testGetDNSWithDnssecValidationChainExtension() {
		System.out.println("--------DNSSEC_RETURN_VALIDATIONCHAIN--------------");
		final IGetDNSContext context = GetDNSFactory.create(1);
		HashMap<ExtensionNames, Object> extensions = new HashMap<ExtensionNames, Object>();
		extensions.put(ExtensionNames.DNSSEC_RETURN_VALIDATION_CHAIN, GetDNSConstants.GETDNS_EXTENSION_TRUE);
		try {
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_INVALID_PARAMETER"));
			HashMap<String, Object> info = context.generalSync(null, RRType.GETDNS_RRTYPE_A, extensions);
		} finally {
			context.close();
		}

	}
}