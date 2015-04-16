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

/**
 * <p>
 * This contains possible context option names that can be used while configuring the context manually. 
 * </p>
 * 
 */
public enum ContextOptionName {
	/**
	 * <p>
	 * An application can change the quering mechanism of a context to be to act
	 * as a stub resolver. Such an application might first get the default
	 * information to make this change from the operating system, probably through
	 * DHCP. Note that if a context is changed to being a stub resolver, this
	 * automatically prevents the application from using the extenstions for
	 * DNSSEC. An application that wants to both do DNSSEC and stub resolution
	 * must do its own DNSSEC processing, possibly with the
	 * getdns_validate_dnssec() function. The list of dicts define where a stub
	 * resolver will send queries. Each dict contains address_data (whose value is
	 * a bindata). For IPv6 link-local addresses, a scope_id name (a bindata) can
	 * be provided. It might also contain port to specify which port to use to
	 * contact these DNS servers; the default is 53. If the stub and a recursive
	 * resolver both support TSIG (RFC 2845), the upstream_list entry can also
	 * contain tsig_algorithm (a bindata) that is the name of the TSIG hash
	 * algorithm, and tsig_secret (a bindata) that is the TSIG key.
	 * </p>
	 */
	UPSTREAMS("upstreams"),

	/**
	 * An application can change the quering mechanism of a context to be to act
	 * as a stub resolver. Such an application might first get the default
	 * information to make this change from the operating system, probably through
	 * DHCP. Note that if a context is changed to being a stub resolver, this
	 * automatically prevents the application from using the extenstions for
	 * DNSSEC. An application that wants to both do DNSSEC and stub resolution
	 * must do its own DNSSEC processing, possibly with the
	 * getdns_validate_dnssec() function. The list of dicts define where a stub
	 * resolver will send queries. Each dict contains address_data (whose value is
	 * a bindata). For IPv6 link-local addresses, a scope_id name (a bindata) can
	 * be provided. It might also contain port to specify which port to use to
	 * contact these DNS servers; the default is 53. If the stub and a recursive
	 * resolver both support TSIG (RFC 2845), the upstream_list entry can also
	 * contain tsig_algorithm (a bindata) that is the name of the TSIG hash
	 * algorithm, and tsig_secret (a bindata) that is the TSIG key.
	 */
	UPSTREAMS_RECURSIVE_SERVERS("upstream_recursive_servers"),

	/**
	 * <p>
	 * This takes values as an integer specifying a timeout for a query,
	 * expressed in milliseconds.
	 * </p>
	 */
	TIMEOUT("timeout"),

	/**
	 * <p>
	 * Specifies what transport is used for DNS lookups. The value must be one of
	 * getdns:
	 * <ul>
	 * <li>ContextOptionValue.GETDNS_TRANSPORT_UDP_FIRST_AND_FALL_BACK_TO_TCP</li>
	 * <li>ContextOptionValue.GETDNS_TRANSPORT_UDP_ONLY</li>
	 * <li>ContextOptionValue.GETDNS_TRANSPORT_TCP_ONLY</li>
	 * <li>ContextOptionValue.GETDNS_TRANSPORT_TCP_ONLY_KEEP_CONNECTIONS_OPEN</li>
	 * </ul>
	 * </p>
	 */
	DNS_TRANSPORT("dns_transport"),

	/**
	 * <p>
	 * Specifies whether DNS queries are performed with nonrecursive lookups or as
	 * a stub resolver. The value is either getdns.GETDNS_RESOLUTION_RECURSING or
	 * getdns.GETDNS_RESOLUTION_STUB.
	 * 
	 * If an implementation of this API is only able to act as a recursive
	 * resolver, setting resolution_type to getdns.GETDNS_RESOLUTION_STUB will
	 * throw an exception.
	 * </p>
	 */
	RESOLUTION_TYPE("resolution_type"),

	/**
	 * <p>
	 * Specifies whether DNS queries are performed with stub or recursive
	 * resolver. <br>
	 * true for stub and false for recursive.
	 * </p>
	 */
	STUB("stub"),

	/**
	 * <p>
	 * This takes values as an boolean either true or false.
	 * </p>
	 */
	USE_THREADS("use_threads"),

	/**
	 * <p>
	 * Specifies whether DNSSEC status should be returned in dns response <br>
	 * the value is either true or false
	 * </p>
	 */
	RETURN_DNSSEC_STATUS("return_dnssec_status"),

	/**
	 * <p>
	 * The namespaces attribute takes an ordered list of namespaces that will be
	 * queried. (Important: this context setting is ignored for the
	 * getdns.general() function; it is used for the other functions.)
	 * </p>
	 * <p>
	 * The allowed values are:
	 * <ul>
	 * <li>ContextOptionValue.GETDNS_NAMESPACE_DNS</li>
	 * <li>
	 * ContextOptionValue.GETDNS_NAMESPACE_LOCALNAMES</li>
	 * <li>
	 * ContextOptionValue.GETDNS_NAMESPACE_NETBIOS</li>
	 * <li>
	 * ContextOptionValue.GETDNS_NAMESPACE_MDNS</li>
	 * <li>
	 * ContextOptionValue.GETDNS_NAMESPACE_NIS</li>
	 * </p>
	 * <p>
	 * When a normal lookup is done, the API does the lookups in the order given
	 * and stops when it gets the first result; a different method with the same
	 * result would be to run the queries in parallel and return when it gets the
	 * first result. Because lookups might be done over different mechanisms
	 * because of the different namespaces, there can be information leakage that
	 * is similar to that seen with POSIX getaddrinfo(). The default is determined
	 * by the OS.
	 * </p>
	 */
	NAMESPACE("namespace"),

	/**
	 * <p>
	 * Specifies whether or not DNS queries follow redirects. The value must be
	 * one of getdns.GETDNS_REDIRECTS_FOLLOW for normal following of redirects
	 * though CNAME and DNAME; or getdns.GETDNS_REDIRECTS_DO_NOT_FOLLOW to cause
	 * any lookups that would have gone through CNAME and DNAME to return the
	 * CNAME or DNAME, not the eventual target.
	 * </p>
	 */
	FOLLOW_REDIRECT("followredirect"),

	/**
	 * <p>
	 * This is used to Specify a suffix to the query string before the API
	 * starts resolving a name. Its value must be one of
	 * <ul>
	 * <li>ContextOptionValue..GETDNS_APPEND_NAME_ALWAYS</li>
	 * <li>ContextOptionValue..GETDNS_APPEND_NAME_ONLY_TO_SINGLE_LABEL_AFTER_FAILURE</li>
	 * <li>
	 * ContextOptionValue..GETDNS_APPEND_NAME_ONLY_TO_MULTIPLE_LABEL_NAME_AFTER_FAILURE</li>
	 * <li>
	 * ContextOptionValue..GETDNS_APPEND_NAME_NEVER</li>
	 * </ul>
	 * This controls whether or not to append the suffix given by suffix.
	 * </p>
	 */
	APPEND_NAME("appendname"),

	/**
	 * <p>
	 * The value of dns_root_servers is a list of dictionaries containing
	 * addresses to be used for looking up top-level domains. Each dict contains:<br>
	 * <b> address_data:</b> a string representation of an IPv4 or IPv6 address
	 * </p>
	 * <p>
	 * For example, the addresses list could look like:
	 * 
	 * <pre>
	 * {@code
	 * HashMap<ContextOptionNames, Object> options = new HashMap<ContextOptionNames, Object>();
	 * Object[][] list = { { "8.8.8.8"}, { "2001:4860:4860::8888"} }
	 * options.put(ContextOptionNames.DNS_ROOT_SERVERS, list)
	 * IGetDNSContext context = null;
	 * context = GetDNSFactory.create(1, options);
	 * }
	 * </pre>
	 * 
	 * </p>
	 */
	DNS_ROOT_SERVERS("dnsrootservers"),

	/**
	 * <p>
	 * This takes values as a list of strings to be appended based on
	 * append_name. The list elements must follow the rules in RFC 4343
	 * </p>
	 */
	SUFFIX("suffix"),

	/**
	 * <p>
	 * This takes values as a list of DNSSEC trust anchors, expressed as
	 * RDATAs from DNSKEY resource records.
	 * </p>
	 */
	DNSSEC_TRUST_ANCHOR("dnssec_trust_anchor"),

	/**
	 * <p>
	 * This takes values as number of seconds of skew that is allowed in
	 * either direction when checking an RRSIG’s Expiration and Inception fields.
	 * The default is 0.
	 * </p>
	 */
	DNSSEC_ALLOWED_SKEW("dnssec_allowed_skew"),

	/**
	 * <p>
	 * This takes values as an integer between 0 and 255, inclusive. The
	 * default is 0.
	 * </p>
	 */
	EDNS_EXTENDED_RCODE("edns_extended_rcode"),

	/**
	 * <p>
	 * This takes values as an integer between 0 and 255, inclusive. The
	 * default is 0.
	 * </p>
	 */
	EDNS_VERSION("edns_version"),

	/**
	 * <p>
	 * This takes values as an integer valued either 0 or 1. The default is
	 * 0.
	 * </p>
	 */
	EDNS_DO_BIT("edns_do_bit"),

	/**
	 * <p>
	 * Specifies limit (an integer value) on the number of outstanding DNS
	 * queries. The API will block itself from sending more queries if it is about
	 * to exceed this value, and instead keep those queries in an internal queue.
	 * The a value of 0 indicates that the number of outstanding DNS queries is
	 * unlimited.
	 * </p>
	 */
	LIMIT_OUTSTANDING_QUERIES("limit_outstanding_queries"),

	/**
	 * <p>
	 * This takes values as an integer between 512 and 65535, inclusive.
	 * The default is 512.
	 * </p>
	 */
	EDNS_MAXIMUM_UDP_PAYLOADSIZE("edns_maximum_udp_payloadSize");

	private String name;

	private ContextOptionName(String name) {

		this.name = name;
	}

	public String getName() {
		return name;
	}

}
