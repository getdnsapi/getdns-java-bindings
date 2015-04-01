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
	 * dnssec status return text
	 */
	public static final String GETDNS_DNSSEC_SECURE = "The record was determined to be secure in DNSSEC";
	public static final String GETDNS_DNSSEC_BOGUS = "The record was determined to be bogus in DNSSEC";
	public static final String GETDNS_DNSSEC_INDETERMINATE = "The record was determined to be bogus in DNSSEC";
	public static final String GETDNS_DNSSEC_INSECURE = "The record was determined to be insecure in DNSSEC";
	public static final String GETDNS_DNSSEC_NOT_PERFORMED = "DNSSEC validation was not performed";

}
