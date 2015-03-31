package com.verisign.getdns;

public enum ContextOptionsEnum {
	
	/* Namespace types */
	GETDNS_NAMESPACE_DNS (500),
	GETDNS_NAMESPACE_LOCALNAMES (501),
	GETDNS_NAMESPACE_NETBIOS (502),
	GETDNS_NAMESPACE_MDNS (503),
	GETDNS_NAMESPACE_NIS (504),
	
	/**
	 * defgroup Namespace types texts
	define GETDNS_NAMESPACE_DNS_TEXT "See getdns_context_set_namespaces()"
	define GETDNS_NAMESPACE_LOCALNAMES_TEXT "See getdns_context_set_namespaces()"
	define GETDNS_NAMESPACE_NETBIOS_TEXT "See getdns_context_set_namespaces()"
	define GETDNS_NAMESPACE_MDNS_TEXT "See getdns_context_set_namespaces()"
	define GETDNS_NAMESPACE_NIS_TEXT "See getdns_context_set_namespaces()"
	 */
	
	
	
	/* Resolution types */
	GETDNS_RESOLUTION_STUB (520),
	GETDNS_RESOLUTION_RECURSING (521),
	
	/**
	 * defgroup Resolution types texts
	define GETDNS_RESOLUTION_STUB_TEXT "See getdns_context_set_resolution_type()"
	define GETDNS_RESOLUTION_RECURSING_TEXT "See getdns_context_set_resolution_type()"
	 */
	
	
	
	/* Redirect policies */
		GETDNS_REDIRECTS_FOLLOW (530),
		GETDNS_REDIRECTS_DO_NOT_FOLLOW (531),
		
		/**
		 * defgroup Redirect policies texts
		define GETDNS_REDIRECTS_FOLLOW_TEXT "See getdns_context_set_follow_redirects()"
		define GETDNS_REDIRECTS_DO_NOT_FOLLOW_TEXT "See getdns_context_set_follow_redirects()"
		 */
		
		
		
	/* Transport arrangements */
	GETDNS_TRANSPORT_UDP_FIRST_AND_FALL_BACK_TO_TCP	(540),
	GETDNS_TRANSPORT_UDP_ONLY (541),
	GETDNS_TRANSPORT_TCP_ONLY (542),
	GETDNS_TRANSPORT_TCP_ONLY_KEEP_CONNECTIONS_OPEN (543),
	
	/**
	 * defgroup Transport arrangements texts
	define GETDNS_TRANSPORT_UDP_FIRST_AND_FALL_BACK_TO_TCP_TEXT "See getdns_context_set_dns_transport()"
	define GETDNS_TRANSPORT_UDP_ONLY_TEXT "See getdns_context_set_dns_transport()"
	define GETDNS_TRANSPORT_TCP_ONLY_TEXT "See getdns_context_set_dns_transport()"
	define GETDNS_TRANSPORT_TCP_ONLY_KEEP_CONNECTIONS_OPEN_TEXT "See getdns_context_set_dns_transport()"
	 */
	
	
	/* Append Names*/
			GETDNS_APPEND_NAME_ALWAYS(550),
			GETDNS_APPEND_NAME_ONLY_TO_SINGLE_LABEL_AFTER_FAILURE(551),
			GETDNS_APPEND_NAME_ONLY_TO_MULTIPLE_LABEL_NAME_AFTER_FAILURE(552),
			GETDNS_APPEND_NAME_NEVER(553);

		/**
		 * \defgroup Suffix appending methods texts
		define GETDNS_APPEND_NAME_ALWAYS_TEXT "See getdns_context_set_append_name()"
		define GETDNS_APPEND_NAME_ONLY_TO_SINGLE_LABEL_AFTER_FAILURE_TEXT "See getdns_context_set_append_name()"
		define GETDNS_APPEND_NAME_ONLY_TO_MULTIPLE_LABEL_NAME_AFTER_FAILURE_TEXT "See getdns_context_set_append_name()"
		define GETDNS_APPEND_NAME_NEVER_TEXT "See getdns_context_set_append_name()"
		 */

	
	private Integer value;
	
	private ContextOptionsEnum(int value) {
		
		this.value = value;
	}
	
	public Integer getvalue() {
    return value;
	}

}
