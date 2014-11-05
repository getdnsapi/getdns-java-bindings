package com.verisign.getdns;

public interface GetDNSConstants {
	
	/**
	 * Extension possible keys.
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
	 * Extension possible values
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
	
}
