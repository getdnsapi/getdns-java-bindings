package com.verisign.getdns;

public enum ExtensionNames {
	
	/**
	 * Possible keys to be used in the request for extensions.
	 */
	DNSSEC_RETURN_VALIDATION_CHAIN("dnssec_return_validation_chain"),
	DNSSEC_RETURN_ONLY_SECURE("dnssec_return_only_secure"),
	DNSSEC_RETURN_STATUS("dnssec_return_status"),
	RETURN_BOTH_V4_AND_V6("return_both_v4_and_v6"),
	RETURN_CALL_DEBUGGING("return_call_debugging"),
	SPECIFY_CLASS("specify_class"),
	ADD_WARNING_FOR_BAD_DNS("add_warning_for_bad_dns"),
	ADD_OPT_PARAMETERS("add_opt_parameters");
	
	private String name;
	
	private ExtensionNames(String name) {
		
		this.name = name;
	}
	
	public String getName() {
    return name;
	}

}
