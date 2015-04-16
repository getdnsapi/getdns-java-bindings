package com.verisign.getdns;

import java.net.UnknownHostException;
import java.util.HashMap;

/**
 * <p>This interface is for calling the API synchronously using a <a href="com/verisign/getdns/IGetDNSCallback.html">callback</a> mechanism.</p>
 * 
 */
public interface IGetDNSContextSync extends IGetDNSContextBase{
	/**
	 * <p>
	 * This API call is used for looking up any type of DNS record.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * 	HashMap<String, Object> info = context.generalSync("getdnsapi.net", RRType.valueOf("CNAME"), null);
	 * 
	 * }
	 * </pre>
	 * 
	 * @param name
	 *          a representation of the query term; usually a string 
	 * @param requestType
	 *          a DNS RR type as a getdns constant (listed here)
	 * @param extensions
	 *          (optional) a dictionary containing attribute/value pairs
	 * @return DNS response
	 * @throws GetDNSException
	 */
	HashMap<String, Object> generalSync(String name, RRType requestType, HashMap<ExtensionName, Object> extensions)
			throws GetDNSException;

	/**
	 * 
	 * <p>
	 * This API call takes argument only as a host name.
	 * </p>
	 * 
	 * 
	 * <p>
	 * There are three critical differences between addressSync() and
	 * getdnsSync() beyond the missing request_type argument:
	 * <ul>
	 * <li><b>In addressSync()</b>, the name argument can only take a host
	 * name.
	 * <li>You do not need to include a return_both_v4_and_v6 extension with the
	 * call in addressSync(): it will always return both IPv4 and IPv6
	 * addresses.</li>
	 * <li>addressSync() always uses all of namespaces from the context, while generalSync() only uses the DNS
	 * namespace</li>
	 * </ul>
	 * </p>
	 * 
	 * 
	 * @param name
	 *          host name
	 * @param extensions
	 *          (optional) a dictionary containing attribute/value pairs
	 * @return DNS response
	 * @throws GetDNSException
	 */
	HashMap<String, Object> addressSync(String name, HashMap<ExtensionName, Object> extensions) throws GetDNSException;

	/**
	 * This API call takes arguments as a domain name for an SRV lookup. The call returns the relevant
	 * SRV information for the name
	 * 
	 * @param name
	 *          domain name
	 * @param extensions
	 *          (optional) a dictionary containing attribute/value pairs
	 * @return DNS response
	 * @throws GetDNSException
	 */
	HashMap<String, Object> serviceSync(String name, HashMap<ExtensionName, Object> extensions) throws GetDNSException;

	/**
	 * <p>
	 * This API call takes both IPV4 AND IPV6 address.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * 	HashMap<String, Object> info = context.hostnameSync("2001:4860:4860::8888", null);
	 * 
	 * }
	 * </pre>
	 * 
	 * @param address
	 *          IPV4 or IPV6 Address
	 * @param extensions
	 * (optional) a dictionary containing attribute/value pairs
	 * @return
	 * @throws GetDNSException
	 * @throws UnknownHostException
	 */
	HashMap<String, Object> hostnameSync(String address, HashMap<ExtensionName, Object> extensions)
			throws GetDNSException, UnknownHostException;
}
