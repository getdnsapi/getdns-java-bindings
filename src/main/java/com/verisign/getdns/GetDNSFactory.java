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

import java.util.HashMap;

/**
 * This class is used to create a getdns context
 * 
 * <p>
 * Many calls in the DNS API require a DNS context. A DNS context contains the
 * information that the API needs in order to process DNS calls, such as the
 * locations of upstream DNS servers, DNSSEC trust anchors, and so on. The
 * internal structure of the DNS context is opaque, and might be different on
 * each OS. A typical application using this API doesn't need to know anything
 * about contexts. Basically, the application creates a default context, uses
 * its functions, and then closes it when done. Context manipulation is
 * available for more DNS-aware programs, but is unlikely to be of interest to
 * applications that just want the results of lookups for A, AAAA, SRV, and PTR
 * records. It is expected that contexts in implementations of the API will not
 * necessarily be thread-safe, but they will not be thread-hostile.
 * </p>
 * </p>A context should not be used by multiple threads: create a new context
 * for use on a different thread. It is just fine for an application to have
 * many contexts, and some DNS-heavy applications will certainly want to have
 * many even if the application uses a single thread. See below for the methods
 * for creating contexts. When the context is used in the API for the first time
 * and setFromOs is 1, the API starts replacing some of the values with values
 * from the OS, such as those that would be found in res_query(3),
 * /etc/resolv.conf, and so on, then proceeds with the new function. Some
 * advanced users will not want the API to change the values to the OS's
 * defaults; if setFromOs is 0, the API will not do any updates to the initial
 * values based on changes in the OS. For example, this might be useful if the
 * API is acting as a stub resolver that is using a specific upstream recursive
 * resolver chosen by the application, not the one that might come back from
 * DHCP. </p>
 * 
 */
public class GetDNSFactory {

	/**
	 * <p>
	 * Creates a context for calling the API synchronously.<br>
	 * That is, when an application calls one of these synchronous functions, the
	 * API gathers all the required information and then returns the result
	 * </p>
	 * 
	 * @param setFromOs
	 *        Specify 1 to use values from the OS, such as those that would be found in res_query(3), /etc/resolv.conf, and so on.
	 * @param contextOptions
	 *        Default options to initialize the context.
	 * @return
	 *        Sync context.
	 * @throws GetDNSException
	 */
	public static IGetDNSContextSync createSync(int setFromOs, HashMap<ContextOptionName, Object> contextOptions)
			throws GetDNSException {
		return createContext(setFromOs, contextOptions);
	}

	private static GetDNSContext createContext(int setFromOs, HashMap<ContextOptionName, Object> contextOptions) {
		GetDNSContext context = new GetDNSContext(setFromOs);
		try {
			context.applyContextOptions(contextOptions);
		} catch (IllegalArgumentException e) {
			// TODO: Can we make exception handling more generic.
			context.close();
			throw e;
		}
		return context;
	}

	/**
	 * <p>
	 * Creates a context for calling the API asynchronously using <a href=
	 * "http://docs.oracle.com/javase/7/docs/api/java/util/concurrent/Future.html"
	 * target="_blank">Future</a>.<br>
	 * That is, when an application calls one of these asynchronous functions, the
	 * API returns a future object which will be populated with the response or
	 * exception when <a href="com/verisign/getdns/IGetDNSContextAsyncWithFuture.html">run()</a> is invoked
	 * </p>
	 * 
	 * @param setFromOs
	 *        Specify 1 to use values from the OS, such as those that would be found in res_query(3), /etc/resolv.conf, and so on.
	 * @param contextOptions
	 *        Default options to initialize the context.
	 * @return
	 *        Future based Async context.
	 * @throws GetDNSException
	 */
	public static IGetDNSContextAsyncWithFuture createAsyncWithFuture(int setFromOs,
			HashMap<ContextOptionName, Object> contextOptions) throws GetDNSException {
		return createContext(setFromOs, contextOptions);
	}

	/**
	 * <p>
	 * Creates a context for calling the API asynchronously using a <a href="com/verisign/getdns/IGetDNSCallback.html">callback</a> mechanism.<br>
	 * That is, when an application calls one of these asynchronous functions and
	 * provides a <a>callback</a>, the API will invoke the callback with the
	 * response or exception when <a href="com/verisign/getdns/IGetDNSContextAsyncWithCallback.html">run()</a> is invoked
	 * </p>
	 * 
	 * 
	 * @param setFromOs
	 *        Specify 1 to use values from the OS, such as those that would be found in res_query(3), /etc/resolv.conf, and so on.
	 * @param contextOptions
	 *        Default options to initialize the context.
	 * @return
	 *        Callback based Async context.
	 * @throws GetDNSException
	 */
	public static IGetDNSContextAsyncWithCallback createAsyncWithCallback(int setFromOs,
			HashMap<ContextOptionName, Object> contextOptions) throws GetDNSException {
		return createContext(setFromOs, contextOptions);
	}
}
