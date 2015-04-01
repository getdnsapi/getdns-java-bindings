package com.verisign.getdns;

public enum ContextOptionNames {
	
	/**
	 * Constants related to Manual Context Configuration
	 */
	UPSTREAMS("upstreams"),
	UPSTREAMS_RECURSIVE_SERVERS("upstream_recursive_servers"),
	TIMEOUT("timeout"),
	DNS_TRANSPORT("dns_transport"),
	RESOLUTION_TYPE("resolution_type"),
	STUB("stub"),
	USE_THREADS("use_threads"),
	RETURN_DNSSEC_STATUS("return_dnssec_status"),
	NAMESPACE("namespace"),
	FOLLOW_REDIRECT("followredirect"),
	APPEND_NAME("appendname"),
	DNS_ROOT_SERVERS("dnsrootservers"),
	SUFFIX("suffix"),
	DNSSEC_TRUST_ANCHOR("dnssec_trust_anchor"),
	DNSSEC_ALLOWED_SKEW("dnssec_allowed_skew"),
	EDNS_EXTENDED_RCODE("edns_extended_rcode"),
	EDNS_VERSION("edns_version"),
	EDNS_DO_BIT("edns_do_bit"),
	LIMIT_OUTSTANDING_QUERIES("limit_outstanding_queries"),
	EDNS_MAXIMUM_UDP_PAYLOADSIZE("edns_maximum_udp_payloadSize");
	


	
	private String name;
	
	private ContextOptionNames(String name) {
		
		this.name = name;
	}
	
	public String getName() {
    return name;
	}

}
