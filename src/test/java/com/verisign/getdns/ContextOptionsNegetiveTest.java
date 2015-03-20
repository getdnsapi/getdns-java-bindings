package com.verisign.getdns;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ContextOptionsNegetiveTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testBasicContextOptions_SetTransport() {
		System.out.println("-----------SetTransport-------------");
		HashMap<String, Object> options = new HashMap<String, Object>();
		options.put(GetDNSConstants.CONTEXT_SET_DNS_TRANSPORT, 100);
		try {
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_CONTEXT_UPDATE_FAIL"));
			GetDNSFactory.create(1, options);
		} catch (Exception e) {
			throw e;
		}
	}

	@Test
	public void testBasicContextOptions_SetStub() {
		System.out.println("-----------SetStub-------------");
		HashMap<String, Object> options = new HashMap<String, Object>();
		options.put(GetDNSConstants.CONTEXT_SET_RESOLUTION_TYPE, 999);
		try {
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_CONTEXT_UPDATE_FAIL"));
			GetDNSFactory.create(1, options);
		} catch (Exception e) {
			throw e;
		}
	}

	@Test
	public void testBasicContextOptions_SetTimeout() {
		System.out.println("-----------SetTimeout-------------");
		HashMap<String, Object> options = new HashMap<String, Object>();
		options.put(GetDNSConstants.CONTEXT_SET_TIMEOUT, 0);
		try {
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_INVALID_PARAMETER"));
			GetDNSFactory.create(1, options);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * test set_upstreams, set stub always true in this case
	 */
	@Test
	public void testBasicContextOptions_SetUpStreams() {
		System.out.println("-----------SetUpStreams-------------");
		HashMap<String, Object> options = new HashMap<String, Object>();
		options.put(GetDNSConstants.CONTEXT_SET_STUB, true);
		Object[][] list = { { 12 }, { 32 } };
		options.put(GetDNSConstants.CONTEXT_SET_UPSTREAMS, list);
		try {
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_CONTEXT_UPDATE_FAIL"));
			GetDNSFactory.create(1, options);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * test setStub; true for stub and false for recursive
	 */
	@Test
	public void testSetStub() {
		System.out.println("-----------SetStub-------------");
		HashMap<String, Object> options = new HashMap<String, Object>();
		options.put(GetDNSConstants.CONTEXT_SET_STUB, 90);
		try {
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_INVALID_PARAMETER"));
			GetDNSFactory.create(1, options);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * threads are allowed if true, otherwise false
	 */
	@Test
	public void testUseThread() {
		System.out.println("-----------SetUseThread-------------");
		HashMap<String, Object> options = new HashMap<String, Object>();
		options.put(GetDNSConstants.CONTEXT_SET_USE_THREADS, 22);
		IGetDNSContext context = null;
		try {
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_INVALID_PARAMETER"));
			GetDNSFactory.create(1, options);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * <need to find more details>
	 */
	@Test
	public void testReturnDnssecStatus() {
		System.out.println("-----------SetReturnDnssecStatus-------------");
		HashMap<String, Object> options = new HashMap<String, Object>();
		options.put(GetDNSConstants.CONTEXT_SET_RETURN_DNSSEC_STATUS, true);
		IGetDNSContext context = null;
		try {
			context = GetDNSFactory.create(1, options);
			HashMap<String, Object> info = context.addressSync("google.com", null);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 403, GetDNSUtil.getinfovalues(info, "dnssec_status"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.close();
		}
	}

	/**
	 * the value is between (0 - 255)
	 */
	@Test
	public void testEdnsExtendedRcode() {
		System.out.println("-----------SetExtendedRcode-------------");
		HashMap<String, Object> options = new HashMap<String, Object>();
		options.put(GetDNSConstants.CONTEXT_SET_EDNS_EXTENDED_RCODE, "");
		try {
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_INVALID_PARAMETER"));
			GetDNSFactory.create(1, options);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * the value is between (0 - 255)
	 */
	@Test
	public void testEdnsVersion() {
		System.out.println("-----------SetEdnsVersion-------------");
		try {
			HashMap<String, Object> options = new HashMap<String, Object>();
			options.put(GetDNSConstants.CONTEXT_SET_EDNS_VERSION, "");
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_INVALID_PARAMETER"));
			GetDNSFactory.create(1, options);
		} catch (Exception e) {
			throw e;
		}
	}

	@Test
	public void testEdnsDoBit() {
		System.out.println("-----------SetEdnsDoBit-------------");
		try {
			HashMap<String, Object> options = new HashMap<String, Object>();
			options.put(GetDNSConstants.CONTEXT_SET_EDNS_DO_BIT, 2);
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_CONTEXT_UPDATE_FAIL"));
			GetDNSFactory.create(1, options);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * testing for Strign parameters
	 */
	@Test
	public void testLimitOutstandingQueries() {
		System.out.println("-----------SetLimitOutStandingQueries-------------");
		try {
			HashMap<String, Object> options = new HashMap<String, Object>();
			options.put(GetDNSConstants.CONTEXT_SET_LIMIT_OUTSTANDING_QUERIES, "four");
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_INVALID_PARAMETER"));
			GetDNSFactory.create(1, options);
		} catch (Exception e) {
			throw e;
		}
	}

	@Test
	public void testEdnsMaxUdpPayloadSize() {
		System.out.println("-----------SetEdnsMaxUdpPayloadSize-------------");
		try {
			HashMap<String, Object> options = new HashMap<String, Object>();
			options.put(GetDNSConstants.CONTEXT_SET_EDNS_MAXIMUM_UDP_PAYLOADSIZE, "");
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_INVALID_PARAMETER"));
			GetDNSFactory.create(1, options);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * test NameSpaces
	 */
	@Test
	public void testNameSpace() {
		System.out.println("-----------SetNameSpace-------------");
		HashMap<String, Object> options = new HashMap<String, Object>();
		try {
			int[] namespace = { ContextOptionsEnum.GETDNS_NAMESPACE_DNS.getvalue(),
					ContextOptionsEnum.GETDNS_NAMESPACE_MDNS.getvalue() };
			options.put(GetDNSConstants.CONTEXT_SET_NAMESPACE, namespace);
			GetDNSFactory.create(1, options);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * test reDirectFollow
	 */
	@Test
	public void testreDirectFollow() {
		System.out.println("-----------SetreDirectFollow-------------");
		try {
			HashMap<String, Object> options = new HashMap<String, Object>();
			options.put(GetDNSConstants.CONTEXT_SET_FOLLOW_REDIRECT, 999);
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_INVALID_PARAMETER"));
			GetDNSFactory.create(1, options);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * test setAppendName TO DO: Needs to implement in future
	 * 
	 * @Test public void testAppendName() {
	 *       System.out.println("-----------SetAppendName-------------"); try {
	 *       HashMap<String, Object> options = new HashMap<String, Object>();
	 *       options.put(GetDNSConstants.CONTEXT_SET_APPEND_NAME,
	 *       ContextOptionsEnum.GETDNS_APPEND_NAME_ALWAYS.getvalue()); Object[][]
	 *       list = { { "www" } }; options.put(GetDNSConstants.CONTEXT_SET_SUFFIX,
	 *       list); thrown.expect(GetDNSException.class); thrown.expect(new
	 *       ErrorCodeMatcher("GETDNS_RETURN_INVALID_PARAMETER"));
	 *       GetDNSFactory.create(1, options); } catch (Exception e) {
	 *       e.printStackTrace(); } }
	 */
	/**
	 * test setDnsRootServers check NULL value
	 */
	@Test
	public void testDnsRootServers() {
		System.out.println("-----------SetDnsRootServers-------------");
		try {
			HashMap<String, Object> options = new HashMap<String, Object>();
			Object[][] list = { {} };
			options.put(GetDNSConstants.CONTEXT_SET_DNS_ROOT_SERVERS, list);
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_INVALID_PARAMETER"));
			GetDNSFactory.create(1, options);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * test setDnssecTrustAnchor TO DO: need to confirm the parameters
	 */
	@Test
	public void testDnssecTrustAnchor() {
		System.out.println("-----------SetDnssecTrustAnchor-------------");
		try {
			HashMap<String, Object> options = new HashMap<String, Object>();
			Object[][] list = { { "" } };
			options.put(GetDNSConstants.CONTEXT_SET_DNSSEC_TRUST_ANCHOR, list);
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_INVALID_PARAMETER"));
			GetDNSFactory.create(1, options);

		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * test setDnssecAllowedSkew
	 */
	@Test
	public void testDnssecAllowedSkew() {
		System.out.println("-----------SetDnssecAllowedSkew-------------");
		try {
			HashMap<String, Object> options = new HashMap<String, Object>();
			options.put(GetDNSConstants.CONTEXT_SET_DNSSEC_ALLOWED_SKEW, true);
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_INVALID_PARAMETER"));
			GetDNSFactory.create(1, options);
		} catch (Exception e) {
			throw e;
		}
	}

}
