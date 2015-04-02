package com.verisign.getdns.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.Test;

import com.verisign.getdns.ContextOptionNames;
import com.verisign.getdns.ContextOptionValues;
import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.GetDNSUtil;
import com.verisign.getdns.IGetDNSContext;

public class ContextOptionsPositiveTest {

	static String testDomain = "verisigninc.com";

	/**
	 * Check setTransport with UDP only
	 */
	@Test
	public void testBasicContextOptions_SetTransport_UDP() {
		System.out.println("-----------Set_Transport_UDP_Only-------------");
		HashMap<ContextOptionNames, Object> options = new HashMap<ContextOptionNames, Object>();
		options.put(ContextOptionNames.DNS_TRANSPORT, ContextOptionValues.GETDNS_TRANSPORT_UDP_ONLY);
		IGetDNSContext context = null;
		try {
			context = GetDNSFactory.create(1, options);
			HashMap<String, Object> info = context.addressSync(testDomain, null);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.close();
		}
	}

	/**
	 * Check setTransport with TCP only
	 */
	@Test
	public void testBasicContextOptions_SetTransport_TCP() {
		System.out.println("-----------Set_Transport_TCP_Only-------------");
		HashMap<ContextOptionNames, Object> options = new HashMap<ContextOptionNames, Object>();
		options.put(ContextOptionNames.STUB, true);
		options.put(ContextOptionNames.DNS_TRANSPORT, ContextOptionValues.GETDNS_TRANSPORT_TCP_ONLY);
		IGetDNSContext context = null;
		try {
			context = GetDNSFactory.create(1, options);
			HashMap<String, Object> info = context.addressSync(testDomain, null);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.close();
		}
	}

	/**
	 * Set Stub as Resolution option
	 */
	@Test
	public void testBasicContextOptions_SetResolution_Stub() {
		System.out.println("-----------SetResolution-------------");
		HashMap<ContextOptionNames, Object> options = new HashMap<ContextOptionNames, Object>();
		options.put(ContextOptionNames.RESOLUTION_TYPE, ContextOptionValues.GETDNS_RESOLUTION_STUB);
		IGetDNSContext context = null;
		try {
			context = GetDNSFactory.create(1, options);
			HashMap<String, Object> info = context.addressSync(testDomain, null);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.close();
		}
	}

	/**
	 * Set recursive as Resolution option
	 */
	@Test
	public void testBasicContextOptions_SetResolution_Recursive() {
		System.out.println("-----------SetResolution-------------");
		HashMap<ContextOptionNames, Object> options = new HashMap<ContextOptionNames, Object>();
		options.put(ContextOptionNames.RESOLUTION_TYPE, ContextOptionValues.GETDNS_RESOLUTION_RECURSING);
		IGetDNSContext context = null;
		try {
			context = GetDNSFactory.create(1, options);
			HashMap<String, Object> info = context.addressSync(testDomain, null);
			assertNotNull(info);
			System.out.println("info: " + info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.close();
		}
	}

	/**
	 * Specifies number of milliseconds the API will wait for request to return.
	 * The default is not specified.
	 */
	@Test
	public void testBasicContextOptions_SetTimeout() {
		System.out.println("-----------SetTimeout-------------");
		HashMap<ContextOptionNames, Object> options = new HashMap<ContextOptionNames, Object>();
		options.put(ContextOptionNames.STUB, true);
		options.put(ContextOptionNames.TIMEOUT, 2000);
		IGetDNSContext context = null;
		try {
			context = GetDNSFactory.create(1, options);
			HashMap<String, Object> info = context.addressSync(testDomain, null);
			assertNotNull(info);
			System.out.println("info: " + info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.close();
		}
	}

	/**
	 * test set_upstreams, set stub always true in this case
	 */
	@Test
	public void testBasicContextOptions_SetUpStreams() {
		System.out.println("-----------SetUpStreams-------------");
		HashMap<ContextOptionNames, Object> options = new HashMap<ContextOptionNames, Object>();
		options.put(ContextOptionNames.STUB, true);
		Object[][] list = { { "8.8.8.8" }, { "127.0.0.1", 80 } };
		options.put(ContextOptionNames.UPSTREAMS, list);
		IGetDNSContext context = null;
		try {
			context = GetDNSFactory.create(1, options);
			HashMap<String, Object> info = context.addressSync(testDomain, null);
			assertNotNull(info);
			System.out.println("info: " + info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.close();
		}
	}

	/**
	 * test setStub; true for stub and false for recursive
	 */
	@Test
	public void testSetStub() {
		System.out.println("-----------SetStub-------------");
		HashMap<ContextOptionNames, Object> options = new HashMap<ContextOptionNames, Object>();
		options.put(ContextOptionNames.STUB, true);
		IGetDNSContext context = null;
		try {
			context = GetDNSFactory.create(1, options);
			HashMap<String, Object> info = context.addressSync(testDomain, null);
			assertNotNull(info);
			System.out.println("info: " + info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.close();
		}
	}

	/**
	 * threads are allowed if true, otherwise false
	 */
	@Test
	public void testUseThread() {
		System.out.println("-----------SetUseThread-------------");
		HashMap<ContextOptionNames, Object> options = new HashMap<ContextOptionNames, Object>();
		options.put(ContextOptionNames.USE_THREADS, true);
		IGetDNSContext context = null;
		try {
			context = GetDNSFactory.create(1, options);
			HashMap<String, Object> info = context.addressSync(testDomain, null);
			assertNotNull(info);
			System.out.println("info: " + info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.close();
		}
	}

	/**
	 * <need to find more details>
	 */
	@Test
	public void testReturnDnssecStatus() {
		System.out.println("-----------SetReturnDnssecStatus-------------");
		HashMap<ContextOptionNames, Object> options = new HashMap<ContextOptionNames, Object>();
		options.put(ContextOptionNames.RETURN_DNSSEC_STATUS, true);
		IGetDNSContext context = null;
		try {
			context = GetDNSFactory.create(1, options);
			HashMap<String, Object> info = context.addressSync(testDomain, null);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 400, GetDNSUtil.getinfovalues(info, "dnssec_status"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.close();
		}
	}

	/**
	 * 
	 */
	@Test
	public void testEdnsExtendedRcode() {
		System.out.println("-----------SetExtendedRcode-------------");
		HashMap<ContextOptionNames, Object> options = new HashMap<ContextOptionNames, Object>();
		options.put(ContextOptionNames.EDNS_EXTENDED_RCODE, 0);
		IGetDNSContext context = null;
		try {
			context = GetDNSFactory.create(1);
			HashMap<String, Object> info = context.addressSync(testDomain, null);
			assertNotNull(info);
			System.out.println("info: " + info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
		} catch (Exception e) {
			e.printStackTrace();
			fail("if any error occurs");
		}
	}

	@Test
	public void testEdnsVersion() {
		System.out.println("-----------SetEdnsVersion-------------");
		try {
			HashMap<ContextOptionNames, Object> options = new HashMap<ContextOptionNames, Object>();
			options.put(ContextOptionNames.EDNS_VERSION, 120);
			GetDNSFactory.create(1, options);
		} catch (Exception e) {
			e.printStackTrace();
			fail("if any error occurs");
		}
	}

	@Test
	public void testEdnsDoBit() {
		System.out.println("-----------SetEdnsDoBit-------------");
		try {
			HashMap<ContextOptionNames, Object> options = new HashMap<ContextOptionNames, Object>();
			options.put(ContextOptionNames.EDNS_DO_BIT, 1);
			GetDNSFactory.create(1, options);
		} catch (Exception e) {
			e.printStackTrace();
			fail("if any error occurs");
		}
	}

	@Test
	public void testLimitOutstandingQueries() {
		System.out.println("-----------SetLimitOutStandingQueries-------------");
		try {
			HashMap<ContextOptionNames, Object> options = new HashMap<ContextOptionNames, Object>();
			options.put(ContextOptionNames.LIMIT_OUTSTANDING_QUERIES, 120);
			GetDNSFactory.create(1, options);
		} catch (Exception e) {
			e.printStackTrace();
			fail("if any error occurs");
		}
	}

	@Test
	public void testEdnsMaxUdpPayloadSize() {
		System.out.println("-----------SetEdnsMaxUdpPayloadSize-------------");
		try {
			HashMap<ContextOptionNames, Object> options = new HashMap<ContextOptionNames, Object>();
			options.put(ContextOptionNames.EDNS_MAXIMUM_UDP_PAYLOADSIZE, 0);
		} catch (Exception e) {
			e.printStackTrace();
			fail("if any error occurs");
		}
	}

	/**
	 * test NameSpaces Note:only accepts DNS and Local files
	 */
	@Test
	public void testNameSpace() {
		System.out.println("-----------SetNameSpace-------------");
		HashMap<ContextOptionNames, Object> options = new HashMap<ContextOptionNames, Object>();

		Object[] namespace = { ContextOptionValues.GETDNS_NAMESPACE_DNS,
				ContextOptionValues.GETDNS_NAMESPACE_MDNS };
		options.put(ContextOptionNames.NAMESPACE, namespace);
		IGetDNSContext context = null;
		try {
			context = GetDNSFactory.create(1, options);
			HashMap<String, Object> info = context.addressSync(testDomain, null);
			assertNotNull(info);
			System.out.println("info: " + info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.close();
		}
	}

	/**
	 * test reDirectFollow
	 */
	@Test
	public void testreDirectFollow() {
		System.out.println("-----------SetreDirectFollow-------------");
		HashMap<ContextOptionNames, Object> options = new HashMap<ContextOptionNames, Object>();
		options.put(ContextOptionNames.FOLLOW_REDIRECT, ContextOptionValues.GETDNS_REDIRECTS_FOLLOW);
		IGetDNSContext context = null;
		try {
			context = GetDNSFactory.create(1, options);
			HashMap<String, Object> info = context.addressSync(testDomain, null);
			assertNotNull(info);
			System.out.println("info: " + info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.close();
		}
	}

	/**
	 * test setAppendName note: method is not implemented in getdns library
	 */
	@Test
	public void testAppendName() {
		System.out.println("-----------SetAppendName-------------");
		HashMap<ContextOptionNames, Object> options = new HashMap<ContextOptionNames, Object>();
		options.put(ContextOptionNames.APPEND_NAME, ContextOptionValues.GETDNS_APPEND_NAME_ALWAYS);
		Object[] list = { "www" };
		options.put(ContextOptionNames.SUFFIX, list);
		IGetDNSContext context = null;
		try {
			context = GetDNSFactory.create(1, options);
			HashMap<String, Object> info = context.addressSync(testDomain, null);
			assertNotNull(info);
			System.out.println("info: " + info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.close();
		}
	}

	/**
	 * test setDnsRootServers note: method is not implemented in getdns library
	 */
	@Test
	public void testDnsRootServers() {
		System.out.println("-----------SetDnsRootServers-------------");
		HashMap<ContextOptionNames, Object> options = new HashMap<ContextOptionNames, Object>();
		Object[][] list = { { "8.8.8.8"}, { "2001:4860:4860::8888"} };
		options.put(ContextOptionNames.DNS_ROOT_SERVERS, list);
		IGetDNSContext context = null;
		try {
			context = GetDNSFactory.create(1, options);
			HashMap<String, Object> info = context.addressSync(testDomain, null);
			assertNotNull(info);
			System.out.println("info: " + info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.close();
		}
	}

	/**
	 * test setDnssecTrustAnchor note: method is not implemented in getdns library
	 */
	@Test
	public void testDnssecTrustAnchor() {
		System.out.println("-----------SetDnssecTrustAnchor-------------");
		HashMap<ContextOptionNames, Object> options = new HashMap<ContextOptionNames, Object>();
		Object[] list = { "AQPDzldNmMvZFX4NcNJ0uEnKDg7tmv/F3MyQR0lpBmVcNcsIszxNFxsB fKNW9JYCYqpik8366LE7VbIcNRzfp2h9OO8HRl+H+E08zauK8k7evWEm u/6od+2boggPoiEfGNyvNPaSI7FOIroDsnw/taggzHRX1Z7SOiOiPWPN IwSUyWOZ79VmcQ1GLkC6NlYvG3HwYmynQv6oFwGv/KELSw7ZSdrbTQ0H XvZbqMUI7BaMskmvgm1G7oKZ1YiF7O9ioVNc0+7ASbqmZN7Z98EGU/Qh 2K/BgUe8Hs0XVcdPKrtyYnoQHd2ynKPcMMlTEih2/2HDHjRPJ2aywIpK Nnv4oPo/" };
		options.put(ContextOptionNames.DNSSEC_TRUST_ANCHOR, list);
		IGetDNSContext context = null;
		try {
			context = GetDNSFactory.create(1, options);
			HashMap<String, Object> info = context.addressSync(testDomain, null);
			assertNotNull(info);
			System.out.println("info: " + info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.close();
		}
	}

	/**
	 * test setDnssecAllowedSkew
	 */
	@Test
	public void testDnssecAllowedSkew() {
		System.out.println("-----------SetDnssecAllowedSkew-------------");
		HashMap<ContextOptionNames, Object> options = new HashMap<ContextOptionNames, Object>();
		options.put(ContextOptionNames.DNSSEC_ALLOWED_SKEW, 2);
		IGetDNSContext context = null;
		try {
			context = GetDNSFactory.create(1, options);
			HashMap<String, Object> info = context.addressSync(testDomain, null);
			assertNotNull(info);
			System.out.println("info: " + info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.close();
		}
	}

}
