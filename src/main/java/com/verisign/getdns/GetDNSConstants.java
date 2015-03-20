package com.verisign.getdns;

/**
 * This class contains important constants for keys and values for GetDNS
 * request and responses.
 * 
 * @author Prithvi
 *
 */
public interface GetDNSConstants {

	/**
	 * Possible keys to be used in the request for extensions.
	 */
	public static final String DNSSEC_RETURN_VALIDATION_CHAIN = "dnssec_return_validation_chain";
	public static final String DNSSEC_RETURN_ONLY_SECURE = "dnssec_return_only_secure";
	public static final String DNSSEC_RETURN_STATUS = "dnssec_return_status";
	public static final String RETURN_BOTH_V4_AND_V6 = "return_both_v4_and_v6";
	public static final String RETURN_CALL_DEBUGGING = "return_call_debugging";
	public static final String SPECIFY_CLASS = "specify_class";
	public static final String ADD_WARNING_FOR_BAD_DNS = "add_warning_for_bad_dns";
	public static final String ADD_OPT_PARAMETERS = "add_opt_parameters";

	/**
	 * Possible values for enabling GetDNS extensions.
	 */
	public static final int GETDNS_EXTENSION_TRUE = 1000;
	public static final int GETDNS_EXTENSION_FALSE = 1001;

	/**
	 * Constants related to passing address.
	 */
	public static final String IPV6 = "IPv6";
	public static final String IPV4 = "IPv4";
	public static final String ADDRESS_DATA = "address_data";
	public static final String ADDRESS_TYPE = "address_type";

	/**
	 * Constants related to Manual Context Configuration
	 */
	public static final String CONTEXT_SET_UPSTREAMS = "upstreams";
	public static final String CONTEXT_SET_UPSTREAMS_RECURSIVE_SERVERS = "upstream_recursive_servers";
	public static final String CONTEXT_SET_TIMEOUT = "timeout";
	public static final String CONTEXT_SET_DNS_TRANSPORT = "dns_transport";
	public static final String CONTEXT_SET_RESOLUTION_TYPE = "resolution_type";
	public static final String CONTEXT_SET_STUB = "stub";
	public static final String CONTEXT_SET_USE_THREADS = "use_threads";
	public static final String CONTEXT_SET_RETURN_DNSSEC_STATUS = "return_dnssec_status";
	public static final String CONTEXT_SET_NAMESPACE = "namespace";
	public static final String CONTEXT_SET_FOLLOW_REDIRECT = "followredirect";
	public static final String CONTEXT_SET_APPEND_NAME = "appendname";
	public static final String CONTEXT_SET_DNS_ROOT_SERVERS = "dnsrootservers";
	public static final String CONTEXT_SET_SUFFIX = "suffix";
	public static final String CONTEXT_SET_DNSSEC_TRUST_ANCHOR = "dnssec_trust_anchor";
	public static final String CONTEXT_SET_DNSSEC_ALLOWED_SKEW = "dnssec_allowed_skew";
	public static final String CONTEXT_SET_EDNS_EXTENDED_RCODE = "edns_extended_rcode";
	public static final String CONTEXT_SET_EDNS_VERSION = "edns_version";
	public static final String CONTEXT_SET_EDNS_DO_BIT = "edns_do_bit";
	public static final String CONTEXT_SET_LIMIT_OUTSTANDING_QUERIES = "limit_outstanding_queries";
	public static final String CONTEXT_SET_EDNS_MAXIMUM_UDP_PAYLOADSIZE = "edns_maximum_udp_payloadSize";

	/**
	 * dnssec status return text
	 */
	public static final String GETDNS_DNSSEC_SECURE = "The record was determined to be secure in DNSSEC";
	public static final String GETDNS_DNSSEC_BOGUS = "The record was determined to be bogus in DNSSEC";
	public static final String GETDNS_DNSSEC_INDETERMINATE = "The record was determined to be bogus in DNSSEC";
	public static final String GETDNS_DNSSEC_INSECURE = "The record was determined to be insecure in DNSSEC";
	public static final String GETDNS_DNSSEC_NOT_PERFORMED = "DNSSEC validation was not performed";

}
