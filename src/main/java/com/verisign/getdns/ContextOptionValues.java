package com.verisign.getdns;

/**
 * 
 * @author Vinay Soni
 *
 */
public enum ContextOptionValues {
	
	/* Namespace types */
	GETDNS_NAMESPACE_DNS (500),
	GETDNS_NAMESPACE_LOCALNAMES (501),
	GETDNS_NAMESPACE_NETBIOS (502),
	GETDNS_NAMESPACE_MDNS (503),
	GETDNS_NAMESPACE_NIS (504),
		
	
	/* Resolution types */
	GETDNS_RESOLUTION_STUB (520),
	GETDNS_RESOLUTION_RECURSING (521),
	
	
	/* Redirect policies */
		GETDNS_REDIRECTS_FOLLOW (530),
		GETDNS_REDIRECTS_DO_NOT_FOLLOW (531),
		
		
	/* Transport arrangements */
	GETDNS_TRANSPORT_UDP_FIRST_AND_FALL_BACK_TO_TCP	(540),
	GETDNS_TRANSPORT_UDP_ONLY (541),
	GETDNS_TRANSPORT_TCP_ONLY (542),
	GETDNS_TRANSPORT_TCP_ONLY_KEEP_CONNECTIONS_OPEN (543),
	
	
	/* Append Names*/
			GETDNS_APPEND_NAME_ALWAYS(550),
			GETDNS_APPEND_NAME_ONLY_TO_SINGLE_LABEL_AFTER_FAILURE(551),
			GETDNS_APPEND_NAME_ONLY_TO_MULTIPLE_LABEL_NAME_AFTER_FAILURE(552),
			GETDNS_APPEND_NAME_NEVER(553);
	
	
	private Integer value;
	
	private ContextOptionValues(int value) {
		
		this.value = value;
	}
	
	public Integer getvalue() {
    return value;
	}

}
