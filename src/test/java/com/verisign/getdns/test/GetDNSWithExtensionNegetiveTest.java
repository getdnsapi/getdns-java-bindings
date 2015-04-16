package com.verisign.getdns.test;

import java.util.HashMap;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.verisign.getdns.ExtensionName;
import com.verisign.getdns.GetDNSConstants;
import com.verisign.getdns.GetDNSException;
import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.IGetDNSContextSync;
import com.verisign.getdns.RRType;

public class GetDNSWithExtensionNegetiveTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	/**
	 * test with dnssec return status value
	 */
	@Test
	public void testGetDNSWithDnssecStatusExtension() {
		System.out.println("--------DNSSEC_RETURN_STATUS TEST--------------");
		final IGetDNSContextSync context = GetDNSFactory.createSync(1,null);
		HashMap<ExtensionName, Object> extensions = new HashMap<ExtensionName, Object>();
		extensions.put(ExtensionName.DNSSEC_RETURN_STATUS, GetDNSConstants.GETDNS_EXTENSION_TRUE);
		try {
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_INVALID_PARAMETER"));
			context.generalSync(null, RRType.A, extensions);
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
		final IGetDNSContextSync context = GetDNSFactory.createSync(1,null);
		HashMap<ExtensionName, Object> extensions = new HashMap<ExtensionName, Object>();
		extensions.put(ExtensionName.DNSSEC_RETURN_ONLY_SECURE, GetDNSConstants.GETDNS_EXTENSION_TRUE);
		try {
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_INVALID_PARAMETER"));
			context.generalSync(null, RRType.A, extensions);
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
		final IGetDNSContextSync context = GetDNSFactory.createSync(1,null);
		HashMap<ExtensionName, Object> extensions = new HashMap<ExtensionName, Object>();
		extensions.put(ExtensionName.DNSSEC_RETURN_VALIDATION_CHAIN, GetDNSConstants.GETDNS_EXTENSION_TRUE);
		try {
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_INVALID_PARAMETER"));
			context.generalSync(null, RRType.A, extensions);
		} finally {
			context.close();
		}

	}
}