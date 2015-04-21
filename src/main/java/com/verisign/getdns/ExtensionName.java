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
 * This contains extension names that can be used during any query.
 * </p>
 *
 */
public enum ExtensionName {

	/**
	 * <p>
	 * This is used by applications that want to handle their own validation by
	 * getting DNSSEC-related records for a particular response. Use the
	 * dnssec_return_validation_chain extension. Set the extension’s value to
	 * getdns.GETDNS_EXTENSION_TRUE to cause a set of additional DNSSEC-related
	 * records needed for validation to be returned in the response object. This
	 * set comes as validation_chain (a list) at the top level of the response
	 * object. This list includes all resource record dicts for all the resource
	 * records (DS, DNSKEY and their RRSIGs) that are needed to perform the
	 * validation from the root up.
	 * </p>
	 */
	DNSSEC_RETURN_VALIDATION_CHAIN("dnssec_return_validation_chain"),

	/**
	 * <p>
	 * If instead of returning the status, you want to only see secure results,
	 * use the dnssec_return_only_secure extension. The extension’s value is set
	 * to getdns.GETDNS_EXTENSION_TRUE to cause only records that the API can
	 * validate as secure with DNSSEC to be returned in the replies_tree and
	 * replies_full lists. No additional names are added to the dict of the
	 * record; the change is that some records might not appear in the results.
	 * When this context option is set, if the API receives DNS replies but none
	 * are determined to be secure, the error code at the top level of the
	 * response object is getdns.GETDNS_RESPSTATUS_NO_SECURE_ANSWERS.
	 * </p>
	 */
	DNSSEC_RETURN_ONLY_SECURE("dnssec_return_only_secure"),

	/**
	 * <p>
	 * This is used to return the DNSSEC status for DNS record in the replies_tree
	 * list, use the dnssec_return_status extension. Set the extension’s value to
	 * getdns.GETDNS_EXTENSION_TRUE to cause the returned status to have the name
	 * dnssec_status added to the other names in the record’s dictionary
	 * (“header”, “question”, and so on). The potential values for that name are
	 * getdns.GETDNS_DNSSEC_SECURE, getdns.GETDNS_DNSSEC_BOGUS,
	 * getdns.GETDNS_DNSSEC_INDETERMINATE, and getdns.GETDNS_DNSSEC_INSECURE.
	 * </p>
	 */
	DNSSEC_RETURN_STATUS("dnssec_return_status"),

	/**
	 * <p>
	 * This is used to get both IPV4 and IPV6 address in single call. Many applications want to get both IPv4 and IPv6 addresses in a single call
	 * so that the results can be processed together. The address() method is able
	 * to do this automatically. If you are using the general() method, you can
	 * enable this with the return_both_v4_and_v6 extension. The extension’s value
	 * must be set to getdns.GETDNS_EXTENSION_TRUE to cause the results to be the
	 * lookup of either A or AAAA records to include any A and AAAA records for
	 * the queried name (otherwise, the extension does nothing). These results are
	 * expected to be usable with Happy Eyeballs systems that will find the best
	 * socket for an application.
	 * </p>
	 */
	RETURN_BOTH_V4_AND_V6("return_both_v4_and_v6"),

	/**
	 * <p>
	 * An application might want to see debugging information for queries such as
	 * the length of time it takes for each query to return to the API. Use the
	 * return_call_debugging extension. The extension's value (an int) is set
	 * toGETDNS_EXTENSION_TRUE to add the name call_debugging (a list) to the top
	 * level of the response object. Each member of the list is a dict that
	 * represents one call made for the call to the API.
	 * </p>
	 * <p>
	 * Each member has the following names:
	 * <ul>
	 * <li><b>query_name</b> (a bindata) is the name that was sent</li>
	 * <li><b> query_type</b> (an int) is the type that was queried for</li>
	 * <li><b> query_to</b> (a bindata) is the address to which the query was sent
	 * </li>
	 * <li><b> start_time</b> (a bindata) is the time the query started in
	 * milliseconds since the epoch, represented as a uint64_t</li>
	 * <li><b> end_time </b> (a bindata) is the time the query was received in
	 * milliseconds since the epoch, represented as a uint64_t</li>
	 * <li><b> entire_reply</b> (a bindata) is the entire response received</li>
	 * <li><b> dnssec_result </b> (an int) is the DNSSEC status</li>
	 * <li><b> GETDNS_DNSSEC_NOT_PERFORMED</b> if DNSSEC validation was not
	 * performed</li>
	 * </ul>
	 * </p>
	 */
	RETURN_CALL_DEBUGGING("return_call_debugging"),

	/**
	 * <p>
	 * The vast majority of DNS requests are made with the Internet (IN) class. To
	 * make a request in a different DNS class, use, the specify_class extension.
	 * The extension's value (an int) contains the class number. Few applications
	 * will ever use this extension.
	 * </p>
	 */
	SPECIFY_CLASS("specify_class"),

	/**
	 * <p>
	 * To receive a warning if a particular response violates some parts of the
	 * DNS standard, use theadd_warning_for_bad_dns extension. The extension's
	 * value (an int) is set to GETDNS_EXTENSION_TRUE to cause each reply in the
	 * replies_tree to contain an additional name, bad_dns (a list). The list is
	 * zero or more ints that indicate types of bad DNS found in that reply.
	 * </p>
	 * <p>
	 * The list of values is:
	 * <ul>
	 * <li><b>GETDNS_BAD_DNS_CNAME_IN_TARGET</b> A DNS query type that does not
	 * allow a target to be a CNAME pointed to a CNAME</li>
	 * <li><b> GETDNS_BAD_DNS_ALL_NUMERIC_LABEL</b> One or more labels in a
	 * returned domain name is all-numeric; this is not legal for a hostname</li>
	 * <li><b> GETDNS_BAD_DNS_CNAME_RETURNED_FOR_OTHER_TYPE</b> A DNS query for a
	 * type other than CNAME returned a CNAME response</li>
	 * </ul>
	 * </p>
	 */
	ADD_WARNING_FOR_BAD_DNS("add_warning_for_bad_dns"),

	/**
	 * <p>
	 * For lookups that need an OPT resource record in the Additional Data
	 * section, use the add_opt_parameters extension. The extension’s value (a
	 * dict) contains the parameters; these are described in more detail in RFC
	 * 2671.
	 * </p>
	 * 
	 * <p>
	 * They are:
	 * 
	 * <ul>
	 * <li><b>maximum_udp_payload_size:</b>
	 * 
	 * an integer between 512 and 65535 inclusive. If not specified it defaults to
	 * the value in the getdns context.</li>
	 * 
	 * <li><b>
	 * 
	 * extended_rcode:</b>
	 * 
	 * an integer between 0 and 255 inclusive. If not specified it defaults to the
	 * value in the getdns context.</li>
	 * 
	 * <li><b>
	 * 
	 * version:</b>
	 * 
	 * an integer betwen 0 and 255 inclusive. If not specified it defaults to 0.</li>
	 * 
	 * <li><b>
	 * 
	 * do_bit:</b>
	 * 
	 * must be either 0 or 1. If not specified it defaults to the value in the
	 * getdns context.</li>
	 * 
	 * <li><b>
	 * 
	 * options:</b>
	 * 
	 * take a list containing dictionaries for each option to be specified. Each
	 * dictionary contains two keys: option_code (an integer) and option_data (in
	 * the form appropriate for that option code).</li>
	 * </ul>
	 * 
	 * It is very important to note that the OPT resource record specified in
	 * the add_opt_parameters extension might not be the same the one that the API
	 * sends in the query. For example, if the application also includes any of
	 * the DNSSEC extensions, the API will make sure that the OPT resource record
	 * sets the resource record appropriately, making the needed changes to the
	 * settings from the add_opt_parameters extension.
	 * 
	 * 
	 * 
	 * <pre>
	 * {@code
	 * 	HashMap<String, Object> optParams = new HashMap<String, Object>();
	 * 	optParams.put(ExtensionNames.ADD_OPT_PARAM_EXTENDED_RCODE.getName(), 128);
	 * 	HashMap<ExtensionNames, Object> extensions = new HashMap<ExtensionNames, Object>();
	 * 	extensions.put(ExtensionNames.ADD_OPT_PARAMETERS, optParams);
	 * }
	 * </pre>
	 * </p>
	 */
	ADD_OPT_PARAMETERS("add_opt_parameters"),

	/**
	 * <p>
	 * This takes values as an integer between 512 and 65535 inclusive. If not
	 * specified it defaults to the value in the getdns context.
	 * </p>
	 */
	ADD_OPT_PARAM_MAX_UDP_PAYLOAD("maximum_udp_payload_size"),

	/**
	 * <p>
	 * This takes values as an integer between 0 and 255 inclusive. If not
	 * specified it defaults to the value in the getdns context.
	 * </p>
	 */
	ADD_OPT_PARAM_EXTENDED_RCODE("extended_rcode"),

	/**
	 * <p>
	 * This takes values as an integer betwen 0 and 255 inclusive. If not
	 * specified it defaults to 0.
	 * </p>
	 */
	ADD_OPT_PARAM_VERSION("version"),

	/**
	 * <p>
	 * This takes value either 0 or 1. If not specified it defaults to the value
	 * in the getdns context.
	 * </p>
	 */
	ADD_OPT_PARAM_DO_BIT("do_bit"),

	/**
	 * <p>
	 * take a list containing dictionaries for each option to be specified. Each
	 * dictionary contains two keys: option_code (an integer) and option_data (in
	 * the form appropriate for that option code).
	 * </p>
	 */
	ADD_OPT_PARAM_OPTIONS("options");

	private String name;

	private ExtensionName(String name) {

		this.name = name;
	}

	public String getName() {
		return name;
	}

}
