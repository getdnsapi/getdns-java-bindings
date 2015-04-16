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
	 * &#64;Override
	 * public void handleResponse(HashMap<String, Object> response, RuntimeException exception) {
	 * checkResponse(domain, "A", response);
	 * });
	 * context.run();
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
	 * This API call is used for looking up any type of DNS record.
	 * </p>
	 * 
	 * <pre>{@code
	 * context.generalAsync("getdnsapi.net", RRType.valueOf("A"), null, new IGetDNSCallback() {
	 * 
	 * &#64;Override
	 * public void handleResponse(HashMap<String, Object> response, RuntimeException exception) {
	 * checkResponse(domain, "A", response);
	 * });
	 * context.run();
	 * </pre>
	 * 
	 * @param name
	 * a representation of the query term; usually a string but must be a
	 *          dict (as described below) in the case of a PTR record lookup
	 * @param requestType
	 * a DNS RR type as a getdns constant (listed here)
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
	 * <p>
	 * This API call is used for looking up any type of DNS record.
	 * </p>
	 * 
	 * <pre>{@code
	 * context.generalAsync("getdnsapi.net", RRType.valueOf("A"), null, new IGetDNSCallback() {
	 * 
	 * &#64;Override
	 * public void handleResponse(HashMap<String, Object> response, RuntimeException exception) {
	 * checkResponse(domain, "A", response);
	 * });
	 * context.run();
	 * </pre>
	 * 
	 * @param name
	 * a representation of the query term; usually a string but must be a
	 *          dict (as described below) in the case of a PTR record lookup
	 * @param requestType
	 * a DNS RR type as a getdns constant (listed here)
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
	 * This API call is used for looking up any type of DNS record.
	 * </p>
	 * 
	 * <pre>{@code
	 * context.generalAsync("getdnsapi.net", RRType.valueOf("A"), null, new IGetDNSCallback() {
	 * 
	 * &#64;Override
	 * public void handleResponse(HashMap<String, Object> response, RuntimeException exception) {
	 * checkResponse(domain, "A", response);
	 * });
	 * context.run();
	 * </pre>
	 * 
	 * @param name
	 * a representation of the query term; usually a string but must be a
	 *          dict (as described below) in the case of a PTR record lookup
	 * @param requestType
	 * a DNS RR type as a getdns constant (listed here)
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
