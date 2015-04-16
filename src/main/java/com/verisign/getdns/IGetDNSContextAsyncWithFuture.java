package com.verisign.getdns;

import java.net.UnknownHostException;
import java.util.HashMap;

/**
 * <p>This interface is for calling the API asynchronously using <a href=
	 * "http://docs.oracle.com/javase/7/docs/api/java/util/concurrent/Future.html"
	 * target="_blank">Future</a>.</p>
 */
public interface IGetDNSContextAsyncWithFuture extends IGetDNSContextBase{
	/**
	 * <p>
	 * This API call is used for looking up any type of DNS record.
	 * </p>
	 * 
	 * <pre>{@code
	 * GetDNSFutureResult result = context.generalAsync("verisigninc.com", RRType.valueOf("A"), null);
	 * HashMap<String, Object> info = null;
	 * context.run();
	 * info = result.get();
	 *
	 * }
	 * </pre>
	 * 
	 * @param name
	 * a representation of the query term, usually a String;
	 * @param requestType
	 * a DNS RR type as a getdns constant (listed here)
	 * @param extensions
	 * (optional) a dictionary containing attribute/value pairs
	 * @return
	 * GetDNSFutureResult
	 * @throws GetDNSException
	 */
	GetDNSFutureResult generalAsync(String name, RRType requestType, HashMap<ExtensionName, Object> extensions)

	throws GetDNSException;
	
	/**
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
	 * @param name
	 *  host name
	 * @param extensions
	 *  (optional) a dictionary containing attribute/value pairs
	 * @return
	 * GetDNSFutureResult
	 * @throws GetDNSException
	 */
	GetDNSFutureResult addressAsync(String name, HashMap<ExtensionName, Object> extensions) throws GetDNSException;

	/**
	 * This API call takes arguments as a domain name for an SRV lookup. The call returns the relevant
	 * SRV information for the name
	 * 
	 * @param name
	 *          domain name
	 * @param extensions
	 *          (optional) a dictionary containing attribute/value pairs
	 * @return
	 * GetDNSFutureResult
	 * @throws GetDNSException
	 */
	GetDNSFutureResult serviceAsync(String name, HashMap<ExtensionName, Object> extensions) throws GetDNSException;

	/**
	 * <p>
	 * This API call takes both IPV4 AND IPV6 address.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * 	HashMap<String, Object> info = context.hostnameAsync("2001:4860:4860::8888", null);
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
	GetDNSFutureResult hostnameAsync(String address, HashMap<ExtensionName, Object> extensions) throws GetDNSException,
			UnknownHostException;
	
	/**
	 * This method is used to wait for completion of pending DNS requests and populate the response in the future objects.
	 */
	void run();

}
