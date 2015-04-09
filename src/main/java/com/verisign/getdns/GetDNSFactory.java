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

public class GetDNSFactory {

	/**
	 * <p>
	 * Many calls in the DNS API require a DNS context. A DNS context contains the
	 * information that the API needs in order to process DNS calls, such as the
	 * locations of upstream DNS servers, DNSSEC trust anchors, and so on. The
	 * internal structure of the DNS context is opaque, and might be different on
	 * each OS. When a context is passed to any function, it must be an allocated
	 * context; the context must not be NULL. A typical application using this API
	 * doesn't need to know anything about contexts. Basically, the application
	 * creates a default context, uses it in the functions that require a context,
	 * and then deallocates it when done. Context manipulation is available for
	 * more DNS-aware programs, but is unlikely to be of interest to applications
	 * that just want the results of lookups for A, AAAA, SRV, and PTR records. It
	 * is expected that contexts in implementations of the API will not
	 * necessarily be thread-safe, but they will not be thread-hostile.
	 * </p>
	 * </p>A context should not be used by multiple threads: create a new context
	 * for use on a different thread. It is just fine for an application to have
	 * many contexts, and some DNS-heavy applications will certainly want to have
	 * many even if the application uses a single thread. See above for the method
	 * for creating and destroying contexts. When the context is used in the API
	 * for the first time and set_from_os is 1, if set_from_os is 0, the API will
	 * not do any updates to the initial values based on changes in the OS. For
	 * example, this might be useful if the API is acting as a stub resolver that
	 * is using a specific upstream recursive resolver chosen by the application,
	 * not the one that might come back from DHCP. </p>
	 * 
	 * <pre>
	 * {@code
	 * context = GetDNSFactory.create(1);
	 * }
	 * </pre>
	 * 
	 * @param setFromOs
	 * @return
	 * @throws GetDNSException
	 */
	public static IGetDNSContext create(int setFromOs) throws GetDNSException {
		return new GetDNSContext(setFromOs);
	}

	/**
	 * <p>
	 * Setting specific values in a context are done with value-specific
	 * functions. The setting functions all return either GETDNS_RETURN_GOOD for
	 * success or GETDNS_RETURN_CONTEXT_UPDATE_FAIL for a failure to update the
	 * context.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * context = GetDNSFactory.create(1,options);
	 * }
	 * </pre>
	 * 
	 * @param setFromOs
	 * @param contextOptions
	 * @return
	 * @throws GetDNSException
	 */
	public static IGetDNSContext create(int setFromOs, HashMap<ContextOptionName, Object> contextOptions)
			throws GetDNSException {
		return createContext(setFromOs, contextOptions);
	}

	private static GetDNSContext createContext(int setFromOs,
			HashMap<ContextOptionName, Object> contextOptions) {
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
	
	public static IGetDNSContextWithCallback createWithCallback(int setFromOs, HashMap<ContextOptionName, Object> contextOptions)
			throws GetDNSException {
		return createContext(setFromOs, contextOptions);
	}
}
