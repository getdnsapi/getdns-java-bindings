/*
* Copyright (c) 2015, Verisign, Inc.
* All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
* * Redistributions of source code must retain the above copyright
* notice, this list of conditions and the following disclaimer.
* * Redistributions in binary form must reproduce the above copyright
* notice, this list of conditions and the following disclaimer in the
* documentation and/or other materials provided with the distribution.
* * Neither the names of the copyright holders nor the
* names of its contributors may be used to endorse or promote products
* derived from this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
* ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
* WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL Verisign, Inc. BE LIABLE FOR ANY
* DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
* ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package com.verisign.getdns;

import java.net.UnknownHostException;
import java.util.HashMap;

/**
 * 
 * This interface contains all the getdns function
 * 
 * @author Prithvi
 *
 */
public interface IGetDNSContext {

	/**
	 * <p>
	 * This method is used for looking up any type of DNS record.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * 	HashMap<String, Object> info = context.generalSync("gmadkat.com", RRType.valueOf("CNAME"), null);
	 * 
	 * 
	 * }
	 * </pre>
	 * 
	 * @param name
	 *          a representation of the query term; usually a string but must be a
	 *          dict (as described below) in the case of a PTR record lookup
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
	 * he name argument can only take a host name.
	 * </p>
	 * 
	 * 
	 * <p>
	 * There are three critical differences between getdns_address() and
	 * getdns_general() beyond the missing request_type argument:
	 * <ul>
	 * <li><b>In getdns_address()</b>, the name argument can only take a host
	 * name.
	 * <li>You do not need to include a return_both_v4_and_v6 extension with the
	 * call in getdns_address(): it will always return both IPv4 and IPv6
	 * addresses.</li>
	 * <li>getdns_address() always uses all of namespaces from the context (to
	 * better emulate getaddrinfo()), while getdns_general() only uses the DNS
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
	 * it must be a domain name for an SRV lookup. The call returns the relevant
	 * SRV information for the name
	 * 
	 * @param name
	 *  domain name
	 * @param extensions
	 * (optional) a dictionary containing attribute/value pairs
	 * @return DNS response
	 * @throws GetDNSException
	 */
	HashMap<String, Object> serviceSync(String name, HashMap<ExtensionName, Object> extensions) throws GetDNSException;

	/**
	 * <p>
	 * it takes both IPV4 AND IPV6 address.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * 	HashMap&lt;String, Object&gt; info = context.hostnameSync(&quot;2001:4860:4860::8888&quot;, null);
	 * 
	 * }
	 * </pre>
	 * 
	 * @param address
	 *          IPV4 or IPV6 Address
	 * @param extensions
	 * @return
	 * @throws GetDNSException
	 * @throws UnknownHostException
	 */
	HashMap<String, Object> hostnameSync(String address, HashMap<ExtensionName, Object> extensions)
			throws GetDNSException, UnknownHostException;

	/**
	 * 
	 * @param name
	 * @param requestType
	 * @param extensions
	 * @return
	 * @throws GetDNSException
	 */
	GetDNSFutureResult generalAsync(String name, RRType requestType, HashMap<ExtensionName, Object> extensions)

	throws GetDNSException;

	/**
	 * 
	 * @param name
	 * @param extensions
	 * @return
	 * @throws GetDNSException
	 */
	GetDNSFutureResult addressAsync(String name, HashMap<ExtensionName, Object> extensions) throws GetDNSException;

	/**
	 * 
	 * @param name
	 * @param extensions
	 * @return
	 * @throws GetDNSException
	 */
	GetDNSFutureResult serviceAsync(String name, HashMap<ExtensionName, Object> extensions) throws GetDNSException;

	/**
	 * 
	 * @param address
	 * @param extensions
	 * @return
	 * @throws GetDNSException
	 * @throws UnknownHostException
	 */
	GetDNSFutureResult hostnameAsync(String address, HashMap<ExtensionName, Object> extensions) throws GetDNSException,
			UnknownHostException;


	void close();

}
