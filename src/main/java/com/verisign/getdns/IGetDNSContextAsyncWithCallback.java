package com.verisign.getdns;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;


/**
 * <p>This interface is for calling the API asynchronously using a <a href="com/verisign/getdns/IGetDNSCallback.html">callback</a> mechanism.</p>
 */
public interface IGetDNSContextAsyncWithCallback extends IGetDNSContextBase{
	
	/**
	 * <p>
	 * This API call is used for looking up any type of DNS record.
	 * </p>
	 * 
	 * <pre>{@code
	 * context.generalAsync("getdnsapi.net", RRType.valueOf("A"), null, new IGetDNSCallback() {
	 * 
	 * public void handleResponse(HashMap<String, Object> response, RuntimeException exception) {
	 * checkResponse(domain, "A", response);
	 * });
	 * context.run();
	 * });
	 * }
	 * </pre>
	 * 
	 * @param name
	 * a representation of the query term; usually a string.
	 * @param requestType
	 * a DNS RR type as a getdns constant (listed here)
	 * @param extensions
	 * (optional) a dictionary containing attribute/value pairs
	 * @param callback
	 * to handle response
	 * @return
	 * 		transactionId
	 * @throws GetDNSException
	 */
	Long generalAsync(String name, RRType requestType, HashMap<ExtensionName, Object> extensions, IGetDNSCallback callback)
			throws GetDNSException;

	
	
	
	
	/**
	 * <p>
	 * This API call takes both IPV4 AND IPV6 address.
	 * </p>
	 * 
	 * @param address
	 *          IPV4 or IPV6 Address
	 * @param extensions
	 * (optional) a dictionary containing attribute/value pairs
	 * @param callback
	 * to handle response
	 * @return
	 * 	transactionId
	 * @throws GetDNSException
	 */
	Long hostnameAsync(String address, HashMap<ExtensionName, Object> extensions, IGetDNSCallback callback)
			throws GetDNSException, UnknownHostException;

	/**
	 * This API call takes arguments as a domain name for an SRV lookup. The call returns the relevant
	 * SRV information for the name
	 * 
	 * @param name
	 * a representation of the query term; usually a string but must be a
	 *          dict (as described below) in the case of a PTR record lookup
	 * @param extensions
	 * (optional) a dictionary containing attribute/value pairs
	 * @param callback
	 * to handle response
	 * @return
	 * 	transactionId
	 * @throws GetDNSException
	 */
	Long serviceAsync(String name, HashMap<ExtensionName, Object> extensions, IGetDNSCallback callback)
			throws GetDNSException;

	/**
	 * <p>
	 * This API call takes argument only as a host name.
	 * </p>
	 * 
	 * 
	 * <p>
	 * There are three critical differences between addressAsync() and
	 * GeneralAsync() beyond the missing request_type argument:
	 * <ul>
	 * <li><b>In addressAsync()</b>, the name argument can only take a host
	 * name.
	 * <li>You do not need to include a return_both_v4_and_v6 extension with the
	 * call in addressAsync(): it will always return both IPv4 and IPv6
	 * addresses.</li>
	 * <li>addressAsync() always uses all of namespaces from the context, while generalSync() only uses the DNS
	 * namespace</li>
	 * </ul>
	 * </p>
	 *
	 * @param name
	 * a representation of the query term; usually a string but must be a
	 *          dict (as described below) in the case of a PTR record lookup
	 * @param extensions
	 * (optional) a dictionary containing attribute/value pairs
	 * @param callback
	 * to handle response
	 * @return
	 * 	transactionId
	 * @throws GetDNSException
	 */
	Long addressAsync(String name, HashMap<ExtensionName, Object> extensions, IGetDNSCallback callback)
			throws GetDNSException;
	
	/**
	 * <p>This function uses the transaction_id to determine which callback is to be cancelled. 
	 * If the function fails, transaction_id is set to 0.
	 * @param transactionId
	 * @throws GetDNSException
	 */
	void cancelRequest(Long transactionId) throws GetDNSException;
	
	/**
	 * <p> This function is used to set a threadpool executor to execute the callback.
	 * @param executor
	 */
	void setExecutor(ExecutorService executor);
	
	/**
	 * This method is used to wait for completion of pending DNS requests and populate the response in the future objects.
	 */
	void run();
}
